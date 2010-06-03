package app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;

final class Class_u {

	private Hashtable var_c61 = new Hashtable();
	private Hashtable var_c69 = new Hashtable();
	private Hashtable var_c71 = new Hashtable();
	private PKCS7 var_c79;
	private byte[] var_c81;

	final byte[] sub_ca1(String paramString) {
		try {
			byte[] arrayOfByte1 = (byte[])this.var_c61.remove(paramString);
			if (null == arrayOfByte1) {
				return null;
			}

			Class_t localClass_t1 = (Class_t)this.var_c69.get(paramString);
			if (null == localClass_t1) {
				return null;
			}

			Class_t localClass_t2 = (Class_t)this.var_c71.get(paramString);
			if (null == localClass_t2) {
				return null;
			}

			MessageDigest localMessageDigest1 = MessageDigest.getInstance("MD5");
			localMessageDigest1.reset();

			localMessageDigest1.update(arrayOfByte1);

			byte[] arrayOfByte2 = localMessageDigest1.digest();
			String str1 = Class_k.sub_23c(arrayOfByte2, -100);

			if (!str1.equals(localClass_t1.var_ba)) {
				return null;
			}

			MessageDigest localMessageDigest2 = MessageDigest.getInstance("SHA");
			localMessageDigest2.reset();
			localMessageDigest2.update(arrayOfByte1);

			byte[] arrayOfByte3 = localMessageDigest2.digest();
			String str2 = Class_k.sub_23c(arrayOfByte3, -91);

			if (!str2.equals(localClass_t1.var_c2)) {
				return null;
			}
			localMessageDigest1.reset();

			localMessageDigest1.update(localClass_t1.var_ca);
			arrayOfByte2 = localMessageDigest1.digest();
			str1 = Class_k.sub_23c(arrayOfByte2, -120);

			if (!str1.equals(localClass_t2.var_ba)) {
				return null;
			}

			localMessageDigest2.reset();
			localMessageDigest2.update(localClass_t1.var_ca);
			arrayOfByte3 = localMessageDigest2.digest();

			str2 = Class_k.sub_23c(arrayOfByte3, -111);
			if (!str2.equals(localClass_t2.var_c2)) {
				return null;
			}

			SignerInfo[] arrayOfSignerInfo = this.var_c79.verify(this.var_c81);
			if ((arrayOfSignerInfo == null) || ((arrayOfSignerInfo.length ^ 0xFFFFFFFF) == -1)) {
				return null;
			}

			ArrayList localArrayList = arrayOfSignerInfo[0].getCertificateChain(this.var_c79);
			if (localArrayList.size() != 2) {
				return null;
			}

			for (int i = 0; i < localArrayList.size(); i++) {
				X509Certificate localX509Certificate = (X509Certificate)localArrayList.get(i);
				String str3 = localX509Certificate.getSerialNumber().toString();

				byte[] arrayOfByte4 = localX509Certificate.getPublicKey().getEncoded();

				String str4 = Class_k.sub_23c(arrayOfByte4, 89);
				if (i == 0) {
					if (!str3.equals("105014014184937810784491209018632141624")) {
						return null;
					}
					if (!str4.equals("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxehHTKQFiy/+t7xlQ0UYmmpQyoohClLm5Gfcy9hqwSps8riRS4LH4F3Ii9XnPYYC85R0wMfsfFCQlqgPbHK4X2iuUNw/bAT8jVHeIAIHPrxBaBqIzq92CHfGmLDDWEMQh+R5EpKW6caR0HB38c/eNYce5Do8DwOIMI/tC0LTcfjkgSjB2G19pT38W/ra1XwFVZR3fL/vvUGPiNDdcCcQCniPjYE1wLI/y9iNDfPcEnL92rhq3g5WVYrZ/CAXHAdQ9wCGBRyRgtVM1AjWYranZI9fNj+h/KjRDa+Fsu+k5gKLiKRNz9PGk+mmrBFOWOWMCsjyOalnkkx+N1/Gh4KcRwIDAQAB")) {
						return null;
					}
				}

				if (-2 == (i ^ 0xFFFFFFFF)) {
					if (!str3.equals("10")) {
						return null;
					}
					if (!str4.equals("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGuLknYK8L45FpZdt+je2R5qrxvtXt/m3ULH/RcHf7JplXtN0/MLjcIepojYGS/C5LkTWEIPLaSrq0/ObaiPIgxSGSCUeVoAkcpnm+sUwd/PGKblTSaaHxTJM6Qf591GR7Y0X3YGAdMR2k6dMPi/tuJiSzqP/l5ZDUtMLcUGCuWQIDAQAB"))
						return null;
				}
			}

			return arrayOfByte1;
		} catch (Exception localException) {
			localException.printStackTrace();
			DialogFactory.ShowError(LanguageStrings.Get("err_get_file") + ":" + paramString + " [" + localException.toString() + "]");
		}
		return null;
	}

	Class_u(byte[] paramArrayOfByte) throws IOException {
		ZipInputStream localZipInputStream = new ZipInputStream(new ByteArrayInputStream(paramArrayOfByte));

		byte[] arrayOfByte1 = new byte[1000];
		do {
			ZipEntry localZipEntry = localZipInputStream.getNextEntry();
			if (localZipEntry == null) {
				return;
			}
			String str1 = localZipEntry.getName();

			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			do {
				int i = localZipInputStream.read(arrayOfByte1, 0, 1000);
				if (i == -1) {
					break;
				}
				localByteArrayOutputStream.write(arrayOfByte1, 0, i);
			} while (true);

			byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
			if ((str1.equals("META-INF/manifest.mf")) || (str1.equals("META-INF/zigbert.sf"))) {
				int j = 0;
				int[] arrayOfInt = new int[1000];

				int k = 0;
				do {
					if (-5 + arrayOfByte2.length <= k)
						break;
					if ((78 == arrayOfByte2[k]) && (-98 == (arrayOfByte2[(k - -1)] ^ 0xFFFFFFFF)) && (-110 == (arrayOfByte2[(k + 2)] ^ 0xFFFFFFFF)) && (101 == arrayOfByte2[(k - -3)]) && (-59 == (arrayOfByte2[(4 + k)] ^ 0xFFFFFFFF)))
						arrayOfInt[(j++)] = k;
					++k;
				} while (true);

				k = 0;
				do {
					if (j <= k) {
						break;
					}
					Class_t localClass_t = new Class_t();

					int l = arrayOfInt[k];
					int i1;
					if ((k - -1 ^ 0xFFFFFFFF) <= (j ^ 0xFFFFFFFF)) {
						i1 = arrayOfByte2.length;
					} else {
						i1 = -1 + arrayOfInt[(1 + k)];
					}

					localClass_t.var_ca = new byte[i1 - l];

					System.arraycopy(arrayOfByte2, l, localClass_t.var_ca, 0, -l + i1);
					int i2 = 0;

					int i3 = 0;
					do {
						if ((localClass_t.var_ca.length ^ 0xFFFFFFFF) >= (i3 ^ 0xFFFFFFFF))
							break;
						if ((10 == localClass_t.var_ca[i3]) || (13 == localClass_t.var_ca[i3])) {
							String str2 = new String(localClass_t.var_ca, i2, i3 + -i2).trim();
						if (str2.startsWith("Name: ")) {
							localClass_t.var_b2 = str2.substring(6);
						}
						if (str2.startsWith("MD5-Digest: ")) {
							localClass_t.var_ba = str2.substring(12);
						}
						i2 = 1 + i3;
						if (str2.startsWith("SHA1-Digest: "))
							localClass_t.var_c2 = str2.substring(13);
						}
						++i3;
					} while (true);

					if (str1.equalsIgnoreCase("META-INF/manifest.mf")) {
						this.var_c69.put(localClass_t.var_b2, localClass_t);
					}
					if (str1.equalsIgnoreCase("META-INF/zigbert.sf")) {
						this.var_c71.put(localClass_t.var_b2, localClass_t);
					}
					++k;
				} while (true);

				if (str1.equalsIgnoreCase("META-INF/zigbert.sf")) {
					this.var_c81 = arrayOfByte2;
				}
				continue;
			}
			if (!str1.equals("META-INF/zigbert.rsa")) {
				this.var_c61.put(str1, arrayOfByte2);
				continue;
			}
			this.var_c79 = new PKCS7(arrayOfByte2);
		} while (true);
	}

} //class Class_u