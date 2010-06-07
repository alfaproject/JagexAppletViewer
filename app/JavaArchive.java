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

final class JavaArchive
{

	private Hashtable<String, byte[]> _files = new Hashtable<String, byte[]>();
	private Hashtable<String, Manifest> _manifests = new Hashtable<String, Manifest>();
	private Hashtable<String, Manifest> _zigberts = new Hashtable<String, Manifest>();
	private PKCS7 _publicKey;
	private byte[] _zigbertData;

	final byte[] ExtractAndValidate(String fileName)
	{
		try {
			byte[] fileData = _files.remove(fileName);
			if (fileData == null) {
				return null;
			}

			Manifest manifest = _manifests.get(fileName);
			if (manifest == null) {
				return null;
			}

			Manifest zigbert = _zigberts.get(fileName);
			if (zigbert == null) {
				return null;
			}

			// initialize md5 and sha1 engines
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			MessageDigest sha1 = MessageDigest.getInstance("SHA");

			// validate md5 hash of file data
			md5.reset();
			md5.update(fileData);
			byte[] rawMd5Hash = md5.digest();
			String md5Hash = Base64.encode(rawMd5Hash);
			if (!md5Hash.equals(manifest.MD5Digest)) {
				return null;
			}

			// validate sha hash of file data
			sha1.reset();
			sha1.update(fileData);
			byte[] rawSha1Hash = sha1.digest();
			String sha1Hash = Base64.encode(rawSha1Hash);
			if (!sha1Hash.equals(manifest.SHA1Digest)) {
				return null;
			}

			// validate md5 hash of manifest data
			md5.reset();
			md5.update(manifest.RawData);
			rawMd5Hash = md5.digest();
			md5Hash = Base64.encode(rawMd5Hash);
			if (!md5Hash.equals(zigbert.MD5Digest)) {
				return null;
			}

			// validate sha1 hash of manifest data
			sha1.reset();
			sha1.update(manifest.RawData);
			rawSha1Hash = sha1.digest();
			sha1Hash = Base64.encode(rawSha1Hash);
			if (!sha1Hash.equals(zigbert.SHA1Digest)) {
				return null;
			}

			// validate authenticy of zigbert data
			SignerInfo[] signers = _publicKey.verify(_zigbertData);
			if ((signers == null) || (signers.length == 0)) {
				return null;
			}

			ArrayList<?> certificates = signers[0].getCertificateChain(_publicKey);
			if (certificates.size() != 2) {
				return null;
			}

			for (int i = 0; i < certificates.size(); i++) {
				X509Certificate certificate = (X509Certificate) certificates.get(i);
				String certSerialNo = certificate.getSerialNumber().toString();
				byte[] certPubKeyRaw = certificate.getPublicKey().getEncoded();
				String certPubKey = Base64.encode(certPubKeyRaw);

				if (i == 0) {
					if (!certSerialNo.equals("105014014184937810784491209018632141624")) {
						return null;
					}
					if (!certPubKey.equals("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxehHTKQFiy/+t7xlQ0UYmmpQyoohClLm5Gfcy9hqwSps8riRS4LH4F3Ii9XnPYYC85R0wMfsfFCQlqgPbHK4X2iuUNw/bAT8jVHeIAIHPrxBaBqIzq92CHfGmLDDWEMQh+R5EpKW6caR0HB38c/eNYce5Do8DwOIMI/tC0LTcfjkgSjB2G19pT38W/ra1XwFVZR3fL/vvUGPiNDdcCcQCniPjYE1wLI/y9iNDfPcEnL92rhq3g5WVYrZ/CAXHAdQ9wCGBRyRgtVM1AjWYranZI9fNj+h/KjRDa+Fsu+k5gKLiKRNz9PGk+mmrBFOWOWMCsjyOalnkkx+N1/Gh4KcRwIDAQAB")) {
						return null;
					}
				}

				if (i == 1) {
					if (!certSerialNo.equals("10")) {
						return null;
					}
					if (!certPubKey.equals("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGuLknYK8L45FpZdt+je2R5qrxvtXt/m3ULH/RcHf7JplXtN0/MLjcIepojYGS/C5LkTWEIPLaSrq0/ObaiPIgxSGSCUeVoAkcpnm+sUwd/PGKblTSaaHxTJM6Qf591GR7Y0X3YGAdMR2k6dMPi/tuJiSzqP/l5ZDUtMLcUGCuWQIDAQAB"))
						return null;
				}
			}

			return fileData;
		} catch (Exception ex) {
			ex.printStackTrace();
			DialogMessage.sub_648(97, Language.getText("err_get_file") + ":" + fileName + " [" + ex.toString() + "]");
		}
		return null;
	}

	JavaArchive(byte[] zippedData)
			throws IOException
	{
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
					int lastByte = (k == nameIndexesCount - 1 ? fileData.length : nameIndexes[k + 1] - 1);
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

}

/*
 * Location: \\.psf\Home\Documents\java\jagexappletviewer\ Qualified Name:
 * app.Class_u JD-Core Version: 0.5.4
 */