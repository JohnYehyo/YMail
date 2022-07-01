package com.rongji.rjsoft.email.config;

import com.rongji.rjsoft.common.util.RSAUtils;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;

/**
 * @description: Jasypt EncryptablePropertyResolver 自定义实现
 * @author: JohnYehyo
 * @create: 2021-10-09 10:46:56
 */
public class MyEncryptablePropertyResolver implements EncryptablePropertyResolver {

    private EncryptablePropertyDetector detector = null;

    public EncryptablePropertyDetector getDetector(){
        if(null == detector){
            detector = new MyEncryptablePropertyDetector();
        }
        return detector;
    }

    static String privateKey = "";

    static {
        privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC05OWpqkn/QX9Xx+uRkW0Z6d4WyPfkgZc6o98XdoHI4gFEI7ZQH5KCJq8tVaNu0qA0CU92gW6qMNEHXekLRMvYxJ12uaBm0kFxGloL5yMYWg4Wi456K9ArBcesa+mrfB0VGDSD0PsiZcADLgfAh5hHKXrcP5MkceeF0HSPX7RLi6cmVVS6car3kROtwhjVOMVaDwflGflg4iegRMAN5OgxE/Pjf0vXRKFyorx50fH3uFQgZlsCsQb/iy02+FLKSY5r5l/kYQVWi7xXD/eufjD1J/evuO9cfM5pSMuHtO4Ar2eRQBpY2G/WEm+Ue/otFccARtqSBX7l7c8ByOT84uD9AgMBAAECggEAEj6r80rbn8UpZN8jIVF7oYt2/PFUH1X6vM1eaO1o9avl2MuJTdeBXWMtc/MZQso3okf2Hcu8L6owyv9ry9kqfjiGcT0TsosuUowH3P95CLG8jgWRXz0gB9bTZs6TiNpBzKjeUxU23ydIEIoJ+h8y+qpyW9iFrL1YTqTRvMw4UQ4fQ+YuBxeiaGTo5CLrupzyr23tybTRNZmyCGvHaBd4dcaejjZTOMkfbh31SkX4zEntax+tpwswtbKV2XEocYiI+bgjtmQih6lXW/tSJg7kOhTW/OXg8KZJeeQRfFG9t5I9gU23JdTItsYBfrzsacISuXZaadTul7qM9BE1Vc3GOQKBgQDqALz2XWkqqTS1ZnZzrbCTVtsw3mg4H0QGOMLTC7gWUMZ3v/3gXS2IV0kz6TCuvo8cF8ljXkT7Ikq9QCbOOZSNqejS4JmkHEJgeRqzjTi762Nj66YPJK4Gt+MFUE7FNjhd8U0OlLEwGkSO0bGm7mn1VoEUNlCsmRCmnSem/mxpjwKBgQDF5hnN7oNenE913N/7sYliaM3biGnqXtynU0xUzslDsRa1gM8OGLYwc4Vl2slEYARcKn9ZImoKvtZ959R3NzRm/jW67VJhxxxkuTdpGgcDwZzgiY8gGcin7ZmyD+zbq0A7mmcPqB2zUZS0CeGEownVtYEmyWmrn3e9AwaoiZzOswKBgBXVecw3DRAI970RUW4bv8UPrqYRqoHKEQvPkgYlKskSQmJ8DJE5wHzfQBpS4NL9PtctNQJLkn4oRfcAnF5TqxPIzcuXX16bsGnWAvLL84Y9QWnaXAU/s+k2kT02sBLgq0PAtyLsJthJKyp0UHI1/rcAmkyrgL2KDq6tLTz8slrrAoGBAKHPnPXGud+YC1ihuvp/lYfDjd6c0Y1y9xRRha4qTmJs39aUdbhe+Ejxi/dWRw8DO01o6iulefNDSvoznoPDvbMg1Cn9dkjAlzS8RNRJFcc6fd0SXNawfmLgYbU4VEDg62pQu0gP/AjtAZucIKtk69uzxuBB+4b6otsHKGCR9IcFAoGAYE8KLbGYYkGEhDsEOqYwzdUCcDQXQ/sMDhAi+9EFQYa/2611aypRnO4Ey/9cUmO0Lod2o2F5CXjqVUCv1c2/jiNiKYJ/ft8FpuLkhitMkLMp9mXaNmpjHjQ6TuP76Er8AWVliDLisUuAKkCv2vbzEuDOWvH5Vz261S2/5fdkPxc=";
    }

    @Override
    public String resolvePropertyValue(String s) {
        EncryptablePropertyDetector encryptablePropertyDetector = getDetector();
        try {
            if (encryptablePropertyDetector.isEncrypted(s)) {
                String s1 = encryptablePropertyDetector.unwrapEncryptedValue(s);
                String str = RSAUtils.decrypt(s1, privateKey);
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
