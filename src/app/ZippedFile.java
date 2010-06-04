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

final class ZippedFile {

	private Hashtable<String, byte[]> _files = new Hashtable<String, byte[]>();
	private Hashtable<String, Manifest> _manifests = new Hashtable<String, Manifest>();
	private Hashtable<String, Manifest> _zigberts = new Hashtable<String, Manifest>();
	private PKCS7 _publicKey;
	private byte[] _zigbertData;

	final byte[] Extract(String paramString) {
		try {
			byte[] arrayOfByte1 = _files.remove(paramString);
			if (null == arrayOfByte1) {
				return null;
			}

			Manifest localClass_t1 = _manifests.get(paramString);
			if (null == localClass_t1) {
				return null;
			}

			Manifest localClass_t2 = _zigberts.get(paramString);
			if (null == localClass_t2) {
				return null;
			}

			MessageDigest localMessageDigest1 = MessageDigest.getInstance("MD5");
			localMessageDigest1.reset();

			localMessageDigest1.update(arrayOfByte1);

			byte[] arrayOfByte2 = localMessageDigest1.digest();
			String str1 = Class_k.sub_23c(arrayOfByte2, -100);

			if (!str1.equals(localClass_t1.MD5Digest)) {
				return null;
			}

			MessageDigest localMessageDigest2 = MessageDigest.getInstance("SHA");
			localMessageDigest2.reset();
			localMessageDigest2.update(arrayOfByte1);

			byte[] arrayOfByte3 = localMessageDigest2.digest();
			String str2 = Class_k.sub_23c(arrayOfByte3, -91);

			if (!str2.equals(localClass_t1.SHA1Digest)) {
				return null;
			}
			localMessageDigest1.reset();

			localMessageDigest1.update(localClass_t1.RawData);
			arrayOfByte2 = localMessageDigest1.digest();
			str1 = Class_k.sub_23c(arrayOfByte2, -120);

			if (!str1.equals(localClass_t2.MD5Digest)) {
				return null;
			}

			localMessageDigest2.reset();
			localMessageDigest2.update(localClass_t1.RawData);
			arrayOfByte3 = localMessageDigest2.digest();

			str2 = Class_k.sub_23c(arrayOfByte3, -111);
			if (!str2.equals(localClass_t2.SHA1Digest)) {
				return null;
			}

			SignerInfo[] arrayOfSignerInfo = _publicKey.verify(_zigbertData);
			if ((arrayOfSignerInfo == null) || ((arrayOfSignerInfo.length ^ 0xFFFFFFFF) == -1)) {
				return null;
			}

			ArrayList localArrayList = arrayOfSignerInfo[0].getCertificateChain(_publicKey);
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

	ZippedFile(byte[] zippedData) throws IOException {
		ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zippedData));

		ZipEntry zipEntry;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			// get file name
			String fileName = zipEntry.getName();

			// get this file from zip
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1000];
			int bytesRead;
			while ((bytesRead = zipInputStream.read(buffer, 0, 1000)) > 0) {
				byteArrayOutputStream.write(buffer, 0, bytesRead);
			}
			byte[] fileData = byteArrayOutputStream.toByteArray();

			// process meta files
			if (fileName.equals("META-INF/manifest.mf") || fileName.equals("META-INF/zigbert.sf")) {

				// get the location of the "Name:" fields
				int[] nameIndexes = new int[1000];
				int nameIndexesCount = 0;
				for (int k = 0; k < fileData.length - 5; k++) {
					if ((fileData[k] == 78) && (fileData[k + 1] == 97) && (fileData[k + 2] == 109) && (fileData[k + 3] == 101) && (fileData[k + 4] == 58)) {
						nameIndexes[nameIndexesCount++] = k;
					}
				}

				for (int k = 0; k < nameIndexesCount; k++) {
					Manifest manifest = new Manifest();

					// get manifest data
					int firstByte = nameIndexes[k];
					int lastByte = (k == nameIndexesCount -1 ? fileData.length : nameIndexes[k + 1] - 1);
					manifest.RawData = new byte[lastByte - firstByte];
					System.arraycopy(fileData, firstByte, manifest.RawData, 0, lastByte - firstByte);

					// get manifest name and digest algorithms
					int propStart = 0;
					for (int propEnd = 0; propEnd < manifest.RawData.length; propEnd++) {
						if ((manifest.RawData[propEnd] == 10) || (manifest.RawData[propEnd] == 13)) {
							String property = new String(manifest.RawData, propStart, propEnd - propStart).trim();

							if (property.startsWith("Name: ")) {
								manifest.Name = property.substring(6);
							}
							if (property.startsWith("MD5-Digest: ")) {
								manifest.MD5Digest = property.substring(12);
							}
							if (property.startsWith("SHA1-Digest: ")) {
								manifest.SHA1Digest = property.substring(13);
							}

							propStart = propEnd + 1;
						}
					}

					if (fileName.equalsIgnoreCase("META-INF/manifest.mf")) {
						_manifests.put(manifest.Name, manifest);
					}
					if (fileName.equalsIgnoreCase("META-INF/zigbert.sf")) {
						_zigberts.put(manifest.Name, manifest);
					}
				}

				if (fileName.equalsIgnoreCase("META-INF/zigbert.sf")) {
					_zigbertData = fileData;
				}
			} else if (fileName.equals("META-INF/zigbert.rsa")) {
				_publicKey = new PKCS7(fileData);
			} else {
				_files.put(fileName, fileData);
			}
		}
	}

} //class ZippedFile