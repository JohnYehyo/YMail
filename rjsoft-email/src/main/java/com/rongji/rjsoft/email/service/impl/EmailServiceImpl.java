package com.rongji.rjsoft.email.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.email.ao.EmailSendAo;
import com.rongji.rjsoft.email.ao.SendEmailAo;
import com.rongji.rjsoft.email.entity.DocInfo;
import com.rongji.rjsoft.email.enums.EmailType;
import com.rongji.rjsoft.email.enums.FolderType;
import com.rongji.rjsoft.email.query.EmailListQuery;
import com.rongji.rjsoft.email.service.IEmailSendService;
import com.rongji.rjsoft.email.service.IEmailService;
import com.rongji.rjsoft.email.vo.EmailDetailsVo;
import com.rongji.rjsoft.email.vo.EmailListVo;
import com.rongji.rjsoft.email.vo.EmailPageVo;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import com.rongji.rjsoft.vo.CommonPage;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.util.*;

/**
 * @description: 邮件服务
 * @author: JohnYehyo
 * @create: 2022-06-30 09:09:39
 */
@Service
public class EmailServiceImpl implements IEmailService {

    @Value("${mail.pop3.protocol}")
    private String POP3_PROTOCOL;

    @Value("${mail.imap.protocol}")
    private String IMAP_PROTOCOL;

    @Value("${mail.host}")
    private String HOST;

    @Value("${mail.smtp.port}")
    private int SMTP_PORT;

    @Value("${mail.pop3.port}")
    private int POP3_PORT;

    @Value("${mail.imap.port}")
    private int IMAP_PORT;

    @Value("${file.path}")
    private String path;

    @Value("${file.email}")
    private String emailPath;

    private static final String TEXT_PLAIN = "text/plain";
    private static final String TEXT_HTML = "text/html";
    private static final String MULTIPARTS = "multipart/*";
    private static final String MESSAGE_RFC822 = "message/rfc822";

    @Autowired
    private IEmailSendService emailSendService;

    /**
     * 发送邮件
     *
     * @param sendEmailAo 参数体
     * @return 发送结果
     */
    @Override
    public boolean sendEmail(SendEmailAo sendEmailAo) {
        MailAccount account = new MailAccount();
        account.setHost(HOST);
        account.setPort(SMTP_PORT);
        account.setAuth(true);
        account.setFrom(sendEmailAo.getFrom());
//        account.setUser(sendEmailAo.getUser());
//        account.setPass(sendEmailAo.getPassword());
        account.setPass("123456");

        if (CollUtil.isEmpty(sendEmailAo.getFiles())) {
            List<String> tos = Arrays.asList(sendEmailAo.getTo().split(";"));
            List<String> ccs = null;
            List<String> bccs = null;
            if (StringUtils.isNotEmpty(sendEmailAo.getCc())) {
                ccs = Arrays.asList(sendEmailAo.getCc().split(";"));
            }
            if (StringUtils.isNotEmpty(sendEmailAo.getBcc())) {
                bccs = Arrays.asList(sendEmailAo.getBcc().split(";"));
            }
            MailUtil.send(account, tos, ccs, bccs,
                    sendEmailAo.getSubject(),
                    sendEmailAo.getContent(),
                    EmailType.TEXT.getCode() != sendEmailAo.getType()
            );
            ThreadUtil.execute(() -> saveToSend(sendEmailAo));
            return true;
        }

//        MailUtil.send(account, CollUtil.newArrayList(sendEmailAo.getTo()), sendEmailAo.getSubject(),
//                sendEmailAo.getContent(), EmailType.TEXT.getCode() != sendEmailAo.getType(), sendEmailAo.getFiles());
        ThreadUtil.execute(() -> saveToSend(sendEmailAo));
        return true;
    }

    /**
     * 保存到发件箱
     *
     * @param sendEmailAo 参数体
     */
    private void saveToSend(SendEmailAo sendEmailAo) {
        EmailSendAo emailSendAo = new EmailSendAo();
        emailSendAo.setEmailHost(HOST);
        emailSendAo.setEmailPort(String.valueOf(IMAP_PORT));
        emailSendAo.setEmailFrom(sendEmailAo.getFrom());
        emailSendAo.setEmailUser(sendEmailAo.getUser());
        emailSendAo.setEmailTo(sendEmailAo.getTo());
        emailSendAo.setEmailCc(sendEmailAo.getCc());
        emailSendAo.setEmailBcc(sendEmailAo.getBcc());
        emailSendAo.setEmailSubject(sendEmailAo.getSubject());
        emailSendAo.setEmailContent(sendEmailAo.getContent());
        emailSendAo.setEmailFiles(sendEmailAo.getFiles());
        emailSendService.saveEmailSend(emailSendAo);
    }

    /**
     * 邮件列表
     *
     * @param emailListQuery 查询对象
     * @return 邮件列表
     */
    @Override
    public EmailPageVo getEmailList(EmailListQuery emailListQuery) {
        Message[] messages = null;
        Session session = getEmailServerSession();
        try {
            IMAPStore store = (IMAPStore) session.getStore(IMAP_PROTOCOL);
            store.connect("bbb@yeh.com", "123456");
            IMAPFolder folder = (IMAPFolder) store.getFolder(emailListQuery.getBox());

            if (folder.exists()) {
                folder.open(Folder.READ_WRITE);
            } else {
                folder.create(Folder.HOLDS_MESSAGES);
                folder.open(Folder.READ_WRITE);
            }

            int start = (emailListQuery.getCurrent() - 1) * emailListQuery.getPageSize() + 1;
            int end = start + emailListQuery.getPageSize() - 1;
            if (end > folder.getMessageCount()) {
                end = folder.getMessageCount();
            }
            messages = folder.getMessages(start, end);
            List<EmailListVo> list = parseMessage(folder, emailListQuery, messages);
            CommonPage<EmailListVo> result = assemblyPage(emailListQuery, folder, list);
            return new EmailPageVo(result, folder.getUnreadMessageCount());
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.FAIL.getCode(), "获取邮件失败");
        }
    }

    /**
     * 邮件详情
     *
     * @param box 文件夹爱
     * @param uid uid
     * @return 邮件详情
     */
    @Override
    public EmailDetailsVo getEmailByUid(String box, int uid) {
        Session session = getEmailServerSession();
        try {
            IMAPStore store = (IMAPStore) session.getStore(IMAP_PROTOCOL);
            store.connect("bbb@yeh.com", "123456");
            IMAPFolder folder = (IMAPFolder) store.getFolder(box);

            if (folder.exists())
                folder.open(Folder.READ_WRITE);
            else {
                folder.create(Folder.HOLDS_MESSAGES);
                folder.open(Folder.READ_WRITE);
            }
            Message message = folder.getMessageByUID(uid);

            StringBuffer bodyText = new StringBuffer();
            String content = getMailContent(message, bodyText).toString();
            EmailDetailsVo emailDetailsVo = new EmailDetailsVo();
            emailDetailsVo.setContent(content);
            //保存附件
            if (isContainAttachment(message)) {
                List<DocInfo> docs = new ArrayList<>();
                MimeMessage msg = (MimeMessage) message;
                InternetAddress address = getFrom((MimeMessage) message);
                saveAttachment(message, path + emailPath, address.getAddress(), address.getPersonal(), msg.getMessageID(), docs);
                emailDetailsVo.setFiles(docs);
            }
            MimeMessage msg = (MimeMessage) message;
            msg.setFlag(Flags.Flag.SEEN, true);
            return emailDetailsVo;
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.FAIL.getCode(), "获取邮件失败");
        }
    }

    /**
     * 修改读取状态
     *
     * @param uidArr uid数组
     * @param status 状态
     * @return 修改结果
     */
    @Override
    public boolean read(long[] uidArr, boolean status) {
        boolean result = true;
        Session session = getEmailServerSession();
        try {
            IMAPStore store = (IMAPStore) session.getStore(IMAP_PROTOCOL);
            store.connect("bbb@yeh.com", "123456");
            IMAPFolder folder = (IMAPFolder) store.getFolder(FolderType.INBOX.getFolder());

            if (folder.exists())
                folder.open(Folder.READ_WRITE);
            else {
                folder.create(Folder.HOLDS_MESSAGES);
                folder.open(Folder.READ_WRITE);
            }
            Message[] messages = folder.getMessagesByUID(uidArr);
            MimeMessage msg;
            for (Message message : messages) {
                msg = (MimeMessage) message;
                msg.setFlag(Flags.Flag.SEEN, status);
            }
        } catch (Exception e) {
            result = false;
            throw new BusinessException(ResponseEnum.FAIL.getCode(), "修改邮件读取状态失败");
        }
        return result;
    }

    /**
     * 删除邮件
     *
     * @param uidArr uid数组
     * @return 删除结果
     */
    @Override
    public boolean delete(long[] uidArr) {
        boolean result = true;
        Session session = getEmailServerSession();
        try {
            IMAPStore store = (IMAPStore) session.getStore(IMAP_PROTOCOL);
            store.connect("bbb@yeh.com", "123456");
            IMAPFolder folder = (IMAPFolder) store.getFolder(FolderType.INBOX.getFolder());

            if (folder.exists())
                folder.open(Folder.READ_WRITE);
            else {
                folder.create(Folder.HOLDS_MESSAGES);
                folder.open(Folder.READ_WRITE);
            }
            Message[] messages = folder.getMessagesByUID(uidArr);
            IMAPFolder deleteFolder = (IMAPFolder) store.getFolder(FolderType.DELETEDBOX.getFolder());
            if (!deleteFolder.exists()) {
                deleteFolder.create(Folder.HOLDS_MESSAGES);
            }

            for (Message message : messages) {
                message.setFlag(Flags.Flag.DELETED, true);
            }
            folder.moveMessages(messages, deleteFolder);

        } catch (Exception e) {
            result = false;
            throw new BusinessException(ResponseEnum.FAIL.getCode(), "删除邮件失败");
        }
        return result;
    }

    private Session getEmailServerSession() {
        Properties prop = System.getProperties();
        //IMAP
        prop.put("mail.store.protocol", IMAP_PROTOCOL);
        prop.put("mail.imap.host", HOST);
        prop.put("mail.imap.port", IMAP_PORT);
        return Session.getInstance(prop);
    }

    /**
     * 组装分页
     *
     * @param emailListQuery 查询条件
     * @param folder         folder对象
     * @param list           邮件列表
     * @return 分页
     * @throws MessagingException
     */
    private CommonPage<EmailListVo> assemblyPage(EmailListQuery emailListQuery, IMAPFolder folder, List<EmailListVo> list) throws MessagingException {
        CommonPage<EmailListVo> result = new CommonPage<>();
        int total = folder.getMessageCount();
        int totalPage = total % (emailListQuery.getPageSize()) > 0 ? total / (emailListQuery.getPageSize()) + 1 : total / (emailListQuery.getPageSize());
        result.setTotalPage((long) totalPage);
        result.setTotal((long) total);
        result.setCurrent(emailListQuery.getCurrent().longValue());
        result.setPageSize(emailListQuery.getPageSize().longValue());
        result.setList(list);
        return result;
    }


    /**
     * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
     * 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
     *
     * @param part
     * @throws Exception
     */
    public StringBuffer getMailContent(Part part, StringBuffer bodyText) throws MessagingException, IOException {
        String contentType = part.getContentType();

        //获得邮件的MimeType类型
        int nameIndex = contentType.indexOf("name");
        boolean conName = false;
        if (nameIndex != -1) {
            conName = true;
        }

        if (part.isMimeType(TEXT_PLAIN) && conName == false) {
            bodyText.append((String) part.getContent());
        } else if (part.isMimeType(TEXT_HTML) && conName == false) {
            bodyText.append((String) part.getContent());
        } else if (part.isMimeType(MULTIPARTS)) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                getMailContent(multipart.getBodyPart(i), bodyText);
            }
        } else if (part.isMimeType(MESSAGE_RFC822)) {
            getMailContent((Part) part.getContent(), bodyText);
        } else {
            LogUtils.info("无此邮件类型");
        }
        return bodyText;
    }

    /**
     * 解析邮件
     *
     * @param folder   Folder
     * @param messages 要解析的邮件列表
     */
    private List<EmailListVo> parseMessage(IMAPFolder folder, EmailListQuery emailListQuery, Message... messages) throws Exception {
        if (messages == null || messages.length < 1) {
            throw new BusinessException(ResponseEnum.FAIL.getCode(), "未找到要解析的邮件!");
        }


        List<EmailListVo> list = new ArrayList<>();

        EmailListVo emailListVo;
        // 解析所有邮件
        for (int i = 0, count = messages.length; i < count; i++) {
            emailListVo = new EmailListVo();
            MimeMessage msg = (MimeMessage) messages[i];
            InternetAddress address = getFrom(msg);
            //限定只读取该发件人的邮件
            if (StringUtils.isNotEmpty(emailListQuery.getFrom()) && !address.getAddress().equals(emailListQuery.getFrom())) {
                continue;
            }
            emailListVo.setUid(folder.getUID(messages[i]));
            emailListVo.setEmailId(msg.getMessageID());
            emailListVo.setSender(address.getPersonal() + "<" + address.getAddress() + ">");
            emailListVo.setSubject(msg.getSubject());
            emailListVo.setRecipient(getReceiveAddress(msg, null));
            emailListVo.setCc(InternetAddress.toString(msg.getRecipients(Message.RecipientType.CC)));
            emailListVo.setTime(LocalDateTimeUtil.of(msg.getSentDate()));
            emailListVo.setRead(messages[i].getFlags().contains(Flags.Flag.SEEN));
            list.add(emailListVo);
        }

        return list;
    }

    private String getUUIDFileName(String fileName) {
        String uuid = UUID.randomUUID().toString().replace("-", "").toString();
        String houZui = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = uuid + houZui;
        return newFileName;
    }

    /**
     * 读取输入流中的数据保存至指定目录
     *
     * @param is       输入流
     * @param fileName 文件名
     * @param destDir  文件存储目录
     * @throws FileNotFoundException
     * @throws IOException
     */
    private DocInfo saveFile(InputStream is, String destDir, String fileName, String email, String sendName, String emailCode)
            throws FileNotFoundException, IOException {
        DocInfo doc = new DocInfo();
        doc.setEmailCode(emailCode);
        doc.setFileDir(destDir);
        doc.setFileName(fileName);
        String uuidFilename = getUUIDFileName(fileName);
        doc.setFilePath(uuidFilename);
        doc.setReceiveMode("");
        doc.setReceiveTime(new Date());
        doc.setSenderEmail(email);
        doc.setSenderName(sendName);
        doc.setCreateTime(new Date());

        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(new File(destDir + uuidFilename)));
        int len = -1;
        while ((len = bis.read()) != -1) {
            bos.write(len);
            bos.flush();
        }
        bos.close();
        bis.close();
        return doc;
    }

    /**
     * 保存附件
     *
     * @param part      邮件中多个组合体中的其中一个组合体
     * @param destDir   附件保存目录
     * @param emailCode 邮件消息号
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void saveAttachment(Part part, String destDir, String email, String sendName, String emailCode, List<DocInfo> docList) throws UnsupportedEncodingException, MessagingException,
            FileNotFoundException, IOException {
        File f = new File(destDir);
        if (!f.exists()) {
            f.mkdirs();
        }
        if (part.isMimeType(MULTIPARTS)) {
            Multipart multipart = (Multipart) part.getContent();    //复杂体邮件
            //复杂体邮件包含多个邮件体
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                //获得复杂体邮件中其中一个邮件体
                BodyPart bodyPart = multipart.getBodyPart(i);
                //某一个邮件体也有可能是由多个邮件体组成的复杂体
                String disp = bodyPart.getDisposition();
                //只处理附件
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT))) { //   || disp.equalsIgnoreCase(Part.INLINE)//这个可能是正文中内联文件
                    InputStream is = bodyPart.getInputStream();
                    docList.add(this.saveFile(is, destDir, decodeText(bodyPart.getFileName()), email, sendName, emailCode));
                } else if (bodyPart.isMimeType(MULTIPARTS)) {
                    saveAttachment(bodyPart, destDir, email, sendName, emailCode, docList);
                } else {
//                    String contentType = bodyPart.getContentType();
//                    if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {
//                    	this.saveFile(bodyPart.getInputStream(), destDir, decodeText(bodyPart.getFileName()),email,sendName,emailCode);
//                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachment((Part) part.getContent(), destDir, email, sendName, emailCode, docList);
        }
    }

    /**
     * 判断邮件中是否包含附件
     *
     * @param part 邮件内容
     * @return 邮件中存在附件返回true，不存在返回false
     * @throws MessagingException
     * @throws IOException
     */
    private boolean isContainAttachment(Part part) throws MessagingException, IOException {
        boolean flag = false;
        if (part.isMimeType(MULTIPARTS)) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                    flag = true;
                } else if (bodyPart.isMimeType(MULTIPARTS)) {
                    flag = isContainAttachment(bodyPart);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.indexOf("application") != -1) {
                        flag = true;
                    }

                    if (contentType.indexOf("name") != -1) {
                        flag = true;
                    }
                }

                if (flag) {
                    break;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = isContainAttachment((Part) part.getContent());
        }
        return flag;
    }

    /**
     * 获得邮件发件人
     *
     * @param msg 邮件内容
     * @return 地址
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private InternetAddress getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
        Address[] froms = msg.getFrom();
        if (froms.length < 1) {
            throw new MessagingException("没有发件人!");
        }

        return (InternetAddress) froms[0];
    }

    /**
     * 文本解码
     *
     * @param encodeText 解码MimeUtility.encodeText(String text)方法编码后的文本
     * @return 解码后的文本
     * @throws UnsupportedEncodingException
     */
    private String decodeText(String encodeText) throws UnsupportedEncodingException {
        if (encodeText == null || "".equals(encodeText)) {
            return "";
        } else {
            return MimeUtility.decodeText(encodeText);
        }
    }

    /**
     * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人
     * <p>Message.RecipientType.TO  收件人</p>
     * <p>Message.RecipientType.CC  抄送</p>
     * <p>Message.RecipientType.BCC 密送</p>
     *
     * @param msg  邮件内容
     * @param type 收件人类型
     * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ...
     * @throws MessagingException
     */
    private String getReceiveAddress(MimeMessage msg, Message.RecipientType type) throws MessagingException {
        StringBuffer receiveAddress = new StringBuffer();
        Address[] addresss = null;
        if (type == null) {
            addresss = msg.getAllRecipients();
        } else {
            addresss = msg.getRecipients(type);
        }

        if (addresss == null || addresss.length < 1) {
            throw new MessagingException("没有收件人!");
        }

        for (Address address : addresss) {
            InternetAddress internetAddress = (InternetAddress) address;
            receiveAddress.append(internetAddress.toUnicodeString()).append(",");
        }

        //删除最后一个逗号
        receiveAddress.deleteCharAt(receiveAddress.length() - 1);

        return receiveAddress.toString();
    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";

        if (message.isMimeType(TEXT_PLAIN)) {
            result = message.getContent().toString();

        } else if (message.isMimeType(MULTIPARTS)) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();

            result = getTextFromMimeMultipart(mimeMultipart);

        }

        return result;

    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType(TEXT_PLAIN)) {
                result = result + bodyPart.getContent();
                break;
            }
            if (bodyPart.isMimeType(TEXT_HTML)) {
                String html = (String) bodyPart.getContent();
                result = result +  Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());

            }

        }

        return result;

    }

}
