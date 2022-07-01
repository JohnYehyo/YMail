//package com.rongji.rjsoft.email.controller;
//
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.extra.mail.MailAccount;
//import cn.hutool.extra.mail.MailUtil;
//import com.alibaba.fastjson.JSON;
//import com.rongji.rjsoft.email.entity.DocInfo;
//import com.rongji.rjsoft.enums.ResponseEnum;
//import com.rongji.rjsoft.exception.BusinessException;
//import com.rongji.rjsoft.vo.ResponseVo;
//import com.sun.mail.imap.IMAPFolder;
//import com.sun.mail.imap.IMAPStore;
//import com.sun.mail.pop3.POP3Folder;
//import com.sun.mail.pop3.POP3Store;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//import javax.mail.internet.MimeUtility;
//import java.io.*;
//import java.util.*;
//
///**
// * @description: 发送邮件
// * @author: JohnYehyo
// * @create: 2022-06-29 10:45:40
// */
//@Api(tags = "邮件")
//@RequestMapping
//@RestController
//public class SendEmailController {
//
//    @Value("${mail.pop3.protocol}")
//    private String POP3_PROTOCOL;
//
//    @Value("${mail.imap.protocol}")
//    private String IMAP_PROTOCOL;
//
//    @Value("${mail.host}")
//    private String HOST;
//
//    @Value("${mail.smtp.port}")
//    private int SMTP_PORT;
//
//    @Value("${mail.pop3.port}")
//    private int POP3_PORT;
//
//    @Value("${mail.imap.port}")
//    private int IMAP_PORT;
//
//
//    @ApiOperation(value = "发送邮件")
//    @PostMapping(value = "send")
//    public ResponseVo sendEmail() {
//        MailAccount account = new MailAccount();
//        account.setHost(HOST);
//        account.setPort(SMTP_PORT);
//        account.setAuth(true);
//        account.setFrom("aaa@yeh.com");
//        account.setUser("aaa@yeh.com");
//        account.setPass("123456");
//
//        String send = MailUtil.send(account, CollUtil.newArrayList("bbb@yeh.com"), "测试", "邮件测试", false);
//        return ResponseVo.success();
//
//    }
//
//    @ApiOperation(value = "查收邮件")
//    @PostMapping(value = "receive")
//    public ResponseVo receivedEmail() {
//        Message[] messages = null;
//        try {
//            Properties prop = System.getProperties();
//
//            //POP3
//            prop.put("mail.store.protocol", POP3_PROTOCOL);
//            prop.put("mail.pop3.host", HOST);
//            prop.put("mail.pop3.port", POP3_PORT);
//            Session session = Session.getInstance(prop);
////            session.setDebug(true);
//            POP3Store store = (POP3Store) session.getStore(POP3_PROTOCOL);
//            store.connect("bbb@yeh.com", "123456");
//            POP3Folder folder = (POP3Folder) store.getFolder("INBOX");
//
//            //IMAP
////            prop.put("mail.store.protocol", IMAP_PROTOCOL);
////            prop.put("mail.imap.host", HOST);
////            prop.put("mail.imap.port", IMAP_PORT);
////            Session session = Session.getInstance(prop);
////            session.setDebug(true);
////            IMAPStore store = (IMAPStore) session.getStore(IMAP_PROTOCOL);
////            store.connect("bbb@yeh.com", "123456");
////            IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
//
//            if (folder.exists()) {
//                folder.open(Folder.READ_WRITE);
//            } else {
//                folder.create(Folder.HOLDS_MESSAGES);
//                folder.open(Folder.READ_WRITE);
//            }
//
//            System.out.println("总邮件数:" + folder.getMessageCount());
//            System.out.println("未读邮件数:" + folder.getUnreadMessageCount());
//            messages = folder.getMessages();
//            parseMessage(folder, messages);
////            messages = folder.getMessages(folder.getMessageCount() - folder.getUnreadMessageCount() + 1, folder.getMessageCount());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseVo.success();
//    }
//
//    @ApiOperation(value = "获取邮件内容")
//    @PostMapping(value = "message/{id}")
//    public ResponseVo message(@PathVariable int id) throws Exception {
//        Properties prop = System.getProperties();
//
//        //POP3
////        prop.put("mail.store.protocol", POP3_PROTOCOL);
////        prop.put("mail.pop3.host", HOST);
////        prop.put("mail.pop3.port", POP3_PORT);
////        Session session = Session.getInstance(prop);
////        POP3Store store = (POP3Store) session.getStore(POP3_PROTOCOL);
////        store.connect("bbb@yeh.com", "123456");
////        POP3Folder folder = (POP3Folder) store.getFolder("INBOX");
//
//        //IMAP
//        prop.put("mail.store.protocol", IMAP_PROTOCOL);
//        prop.put("mail.imap.host", HOST);
//        prop.put("mail.imap.port", IMAP_PORT);
//        Session session = Session.getInstance(prop);
//        IMAPStore store = (IMAPStore) session.getStore(IMAP_PROTOCOL);
//        store.connect("bbb@yeh.com", "123456");
//        IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
//
//        if (folder.exists())
//            folder.open(Folder.READ_WRITE);
//        else {
//            folder.create(Folder.HOLDS_MESSAGES);
//            folder.open(Folder.READ_WRITE);
//        }
//        Message message = folder.getMessage(id);
//        StringBuffer bodyText = new StringBuffer();
//        return ResponseVo.response(ResponseEnum.SUCCESS, getMailContent(message, bodyText));
//    }
//
//    /**
//     * 解析邮件
//     *
//     * @param folder   Folder
//     * @param messages 要解析的邮件列表
//     */
//    private void parseMessage(IMAPFolder folder, Message... messages) throws Exception {
//        if (messages == null || messages.length < 1) {
//            throw new BusinessException(ResponseEnum.FAIL.getCode(), "未找到要解析的邮件!");
//        }
//
//
//        StringBuffer bodyText;
//
//        // 解析所有邮件
//        for (int i = 0, count = messages.length; i < count; i++) {
//            bodyText = new StringBuffer();
//            MimeMessage msg = (MimeMessage) messages[i];
//            InternetAddress address = getFrom(msg);
//            String receiveAddress = getReceiveAddress(msg, null);
//            //限定只读取该发件人的邮件
//            if (!address.getAddress().equals("aaa@yeh.com")) {
//                continue;
//            }
//            msg.setFlag(Flags.Flag.SEEN, true);
//            System.out.println("------------分界线--------------");
//            System.out.println("uid:" + folder.getUID(messages[i]));
//            System.out.println("邮件id:" + msg.getMessageID());
//            System.out.println("发件人:" + address.getPersonal() + "<" + address.getAddress() + ">");
//            System.out.println("标题:" + msg.getSubject());
//            System.out.println("内容:" + getMailContent(messages[i], bodyText));
//            System.out.println("转码后的标题:" + decodeText(msg.getSubject()));
//            System.out.println("收件人:" + getReceiveAddress(msg, null));
//            System.out.println("抄送人:" + InternetAddress.toString(msg.getRecipients(Message.RecipientType.CC)));
//            System.out.println("密送人:" + InternetAddress.toString(msg.getRecipients(Message.RecipientType.BCC)));
//            System.out.println("收件日期:" + msg.getSentDate());
//
//            //保存附件
//            if (isContainAttachment(msg)) {
//                List<DocInfo> docList = new ArrayList<>();
//                saveAttachment(msg, "D:/email/file/", address.getAddress(), address.getPersonal(), msg.getMessageID(), docList);
//                System.out.println("附件:" + JSON.toJSONString(docList));
//            }
//
//
//        }
//    }
//
//    /**
//     * 解析邮件
//     *
//     * @param folder   Folder
//     * @param messages 要解析的邮件列表
//     */
//    private void parseMessage(POP3Folder folder, Message... messages) throws Exception {
//        if (messages == null || messages.length < 1) {
//            throw new BusinessException(ResponseEnum.FAIL.getCode(), "未找到要解析的邮件!");
//        }
//
//
//        StringBuffer bodyText;
//
//        // 解析所有邮件
//        for (int i = 0, count = messages.length; i < count; i++) {
//            bodyText = new StringBuffer();
//            MimeMessage msg = (MimeMessage) messages[i];
//            InternetAddress address = getFrom(msg);
//            String receiveAddress = getReceiveAddress(msg, null);
////            //限定只读取该发件人的邮件
//            if (!address.getAddress().equals("aaa@yeh.com")) {
//                continue;
//            }
//            msg.setFlag(Flags.Flag.SEEN, true);
//            System.out.println("------------分界线--------------");
//            System.out.println("uid:" + folder.getUID(messages[i]));
//            System.out.println("邮件id:" + msg.getMessageID());
//            System.out.println("发件人:" + address.getPersonal() + "<" + address.getAddress() + ">");
//            System.out.println("标题:" + msg.getSubject());
////            System.out.println("内容:" + getMailContent(messages[i], bodyText));
//            System.out.println("转码后的标题:" + decodeText(msg.getSubject()));
//            System.out.println("收件人:" + getReceiveAddress(msg, null));
//            System.out.println("抄送人:" + InternetAddress.toString(msg.getRecipients(Message.RecipientType.CC)));
//            System.out.println("密送人:" + InternetAddress.toString(msg.getRecipients(Message.RecipientType.BCC)));
//            System.out.println("收件日期:" + msg.getSentDate());
//
//            //保存附件
//            if (isContainAttachment(msg)) {
//                List<DocInfo> docList = new ArrayList<>();
//                saveAttachment(msg, "D:/email/file/", address.getAddress(), address.getPersonal(), msg.getMessageID(), docList);
//                System.out.println("附件:" + JSON.toJSONString(docList));
//            }
//
//
//        }
//    }
//
//    private String getUUIDFileName(String fileName) {
//        String uuid = UUID.randomUUID().toString().replace("-", "").toString();
//        String houZui = fileName.substring(fileName.lastIndexOf("."));
//        String newFileName = uuid + houZui;
//        return newFileName;
//    }
//
//    /**
//     * 读取输入流中的数据保存至指定目录
//     *
//     * @param is       输入流
//     * @param fileName 文件名
//     * @param destDir  文件存储目录
//     * @throws FileNotFoundException
//     * @throws IOException
//     */
//    private DocInfo saveFile(InputStream is, String destDir, String fileName, String email, String sendName, String emailCode)
//            throws FileNotFoundException, IOException {
//        //附件格式过滤
////    	if(!TypeCastUtil.equals(RmdeskConfig.extname, TypeCastUtil.getFileDot(fileName))){
////    		return;
////    	}
//        DocInfo doc = new DocInfo();
//        doc.setEmailCode(emailCode);
//        doc.setFileDir(destDir);
//        doc.setFileName(fileName);
//        String uuidFilename = getUUIDFileName(fileName);
//        doc.setFilePath(uuidFilename);
//        doc.setReceiveMode("");
//        doc.setReceiveTime(new Date());
//        doc.setSenderEmail(email);
//        doc.setSenderName(sendName);
//        doc.setCreateTime(new Date());
//        System.out.println(doc);
//
//        BufferedInputStream bis = new BufferedInputStream(is);
//        BufferedOutputStream bos = new BufferedOutputStream(
//                new FileOutputStream(new File(destDir + uuidFilename)));
//        int len = -1;
//        while ((len = bis.read()) != -1) {
//            bos.write(len);
//            bos.flush();
//        }
//        bos.close();
//        bis.close();
//        return doc;
//    }
//
//    /**
//     * 保存附件
//     *
//     * @param part      邮件中多个组合体中的其中一个组合体
//     * @param destDir   附件保存目录
//     * @param emailCode 邮件消息号
//     * @throws UnsupportedEncodingException
//     * @throws MessagingException
//     * @throws FileNotFoundException
//     * @throws IOException
//     */
//    private void saveAttachment(Part part, String destDir, String email, String sendName, String emailCode, List<DocInfo> docList) throws UnsupportedEncodingException, MessagingException,
//            FileNotFoundException, IOException {
//        File f = new File(destDir);
//        if (!f.exists()) {
//            f.mkdirs();
//        }
//        if (part.isMimeType("multipart/*")) {
//            Multipart multipart = (Multipart) part.getContent();    //复杂体邮件
//            //复杂体邮件包含多个邮件体
//            int partCount = multipart.getCount();
//            for (int i = 0; i < partCount; i++) {
//                //获得复杂体邮件中其中一个邮件体
//                BodyPart bodyPart = multipart.getBodyPart(i);
//                //某一个邮件体也有可能是由多个邮件体组成的复杂体
//                String disp = bodyPart.getDisposition();
//                //只处理附件
//                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT))) { //   || disp.equalsIgnoreCase(Part.INLINE)//这个可能是正文中内联文件
//                    InputStream is = bodyPart.getInputStream();
//                    docList.add(this.saveFile(is, destDir, decodeText(bodyPart.getFileName()), email, sendName, emailCode));
//                } else if (bodyPart.isMimeType("multipart/*")) {
//                    saveAttachment(bodyPart, destDir, email, sendName, emailCode, docList);
//                } else {
////                    String contentType = bodyPart.getContentType();
////                    if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {
////                    	this.saveFile(bodyPart.getInputStream(), destDir, decodeText(bodyPart.getFileName()),email,sendName,emailCode);
////                    }
//                }
//            }
//        } else if (part.isMimeType("message/rfc822")) {
//            saveAttachment((Part) part.getContent(), destDir, email, sendName, emailCode, docList);
//        }
//    }
//
//    /**
//     * 判断邮件中是否包含附件
//     *
//     * @param part 邮件内容
//     * @return 邮件中存在附件返回true，不存在返回false
//     * @throws MessagingException
//     * @throws IOException
//     */
//    private boolean isContainAttachment(Part part) throws MessagingException, IOException {
//        boolean flag = false;
//        if (part.isMimeType("multipart/*")) {
//            MimeMultipart multipart = (MimeMultipart) part.getContent();
//            int partCount = multipart.getCount();
//            for (int i = 0; i < partCount; i++) {
//                BodyPart bodyPart = multipart.getBodyPart(i);
//                String disp = bodyPart.getDisposition();
//                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
//                    flag = true;
//                } else if (bodyPart.isMimeType("multipart/*")) {
//                    flag = isContainAttachment(bodyPart);
//                } else {
//                    String contentType = bodyPart.getContentType();
//                    if (contentType.indexOf("application") != -1) {
//                        flag = true;
//                    }
//
//                    if (contentType.indexOf("name") != -1) {
//                        flag = true;
//                    }
//                }
//
//                if (flag) {
//                    break;
//                }
//            }
//        } else if (part.isMimeType("message/rfc822")) {
//            flag = isContainAttachment((Part) part.getContent());
//        }
//        return flag;
//    }
//
//    /**
//     * 获得邮件发件人
//     *
//     * @param msg 邮件内容
//     * @return 地址
//     * @throws MessagingException
//     * @throws UnsupportedEncodingException
//     */
//    private InternetAddress getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
//        Address[] froms = msg.getFrom();
//        if (froms.length < 1) {
//            throw new MessagingException("没有发件人!");
//        }
//
//        return (InternetAddress) froms[0];
//    }
//
//    /**
//     * 文本解码
//     *
//     * @param encodeText 解码MimeUtility.encodeText(String text)方法编码后的文本
//     * @return 解码后的文本
//     * @throws UnsupportedEncodingException
//     */
//    private String decodeText(String encodeText) throws UnsupportedEncodingException {
//        if (encodeText == null || "".equals(encodeText)) {
//            return "";
//        } else {
//            return MimeUtility.decodeText(encodeText);
//        }
//    }
//
//    /**
//     * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人
//     * <p>Message.RecipientType.TO  收件人</p>
//     * <p>Message.RecipientType.CC  抄送</p>
//     * <p>Message.RecipientType.BCC 密送</p>
//     *
//     * @param msg  邮件内容
//     * @param type 收件人类型
//     * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ...
//     * @throws MessagingException
//     */
//    private String getReceiveAddress(MimeMessage msg, Message.RecipientType type) throws MessagingException {
//        StringBuffer receiveAddress = new StringBuffer();
//        Address[] addresss = null;
//        if (type == null) {
//            addresss = msg.getAllRecipients();
//        } else {
//            addresss = msg.getRecipients(type);
//        }
//
//        if (addresss == null || addresss.length < 1) {
//            throw new MessagingException("没有收件人!");
//        }
//
//        for (Address address : addresss) {
//            InternetAddress internetAddress = (InternetAddress) address;
//            receiveAddress.append(internetAddress.toUnicodeString()).append(",");
//        }
//
//        //删除最后一个逗号
//        receiveAddress.deleteCharAt(receiveAddress.length() - 1);
//
//        return receiveAddress.toString();
//    }
//
//    private String getTextFromMessage(Message message) throws MessagingException, IOException {
//        String result = "";
//
//        if (message.isMimeType("text/plain")) {
//            result = message.getContent().toString();
//
//        } else if (message.isMimeType("multipart/*")) {
//            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
//
//            result = getTextFromMimeMultipart(mimeMultipart);
//
//        }
//
//        return result;
//
//    }
//
//    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
//        String result = "";
//        int count = mimeMultipart.getCount();
//        for (int i = 0; i < count; i++) {
//            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
//            if (bodyPart.isMimeType("text/plain")) {
//                result = result + bodyPart.getContent();
//                break;
//            }
//            if (bodyPart.isMimeType("text/html")) {
//                String html = (String) bodyPart.getContent();
////                result = result +  org.jsoup.Jsoup.parse(html).text();
//                result = result;
//            } else if (bodyPart.getContent() instanceof MimeMultipart) {
//                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
//
//            }
//
//        }
//
//        return result;
//
//    }
//
//    /**
//     * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
//     * 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
//     *
//     * @param part
//     * @throws Exception
//     */
//    public StringBuffer getMailContent(Part part, StringBuffer bodyText) throws Exception {
//        String contentType = part.getContentType();
//
//        //获得邮件的MimeType类型
////        System.out.println("邮件的MimeType类型:" + contentType);
//        int nameIndex = contentType.indexOf("name");
//        boolean conName = false;
//        if (nameIndex != -1) {
//            conName = true;
//        }
////        System.out.println("邮件内容的类型:　" + contentType);
//        if (part.isMimeType("text/plain") && conName == false) {
//            //text/plain类型
//            bodyText.append((String) part.getContent());
//        } else if (part.isMimeType("text/html") && conName == false) {
//            //text/html类型
//            bodyText.append((String) part.getContent());
//        } else if (part.isMimeType("multipart/*")) {
//            //multipart/*
//            Multipart multipart = (Multipart) part.getContent();
//            int counts = multipart.getCount();
//            for (int i = 0; i < counts; i++) {
//                getMailContent(multipart.getBodyPart(i), bodyText);
//            }
//        } else if (part.isMimeType("message/rfc822")) {
//            //message/rfc822
//            getMailContent((Part) part.getContent(), bodyText);
//        } else {
//            System.out.println("");
//        }
//        return bodyText;
//    }
//
//}
