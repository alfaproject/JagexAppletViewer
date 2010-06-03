package app;

final class Class_k
{
  private static char[] var_20c = new char[64];
  private static char[] var_214;
  private static int[] var_21c;

  static final String sub_23c(byte[] paramArrayOfByte, int paramInt)
  {
    return sub_279(paramArrayOfByte, 0, false, paramArrayOfByte.length);
  }

  private static final String sub_279(byte[] paramArrayOfByte, int paramInt1, boolean paramBoolean, int paramInt2)
  {
    boolean bool = Preferences.dummy; StringBuffer localStringBuffer = new StringBuffer();
    if (paramBoolean) {
      sub_279((byte[])null, -78, false, 100);
    }

		for (int i = paramInt1; i < paramInt1 + paramInt2; i += 3) {
			int j = 0xFF & paramArrayOfByte[i];
			localStringBuffer.append(var_20c[(j >>> 117615202)]);
			if (paramInt2 - 1 <= i) {
				localStringBuffer.append(var_20c[(j << -646506524 & 0x30)]).append("==");
				continue;
			}
			int k = 0xFF & paramArrayOfByte[(i + 1)];
			localStringBuffer.append(var_20c[((0x3 & j) << 1966002980 | k >>> -784587996)]);
			if ((paramInt2 - 2 ^ 0xFFFFFFFF) < (i ^ 0xFFFFFFFF)) {
				int l = 0xFF & paramArrayOfByte[(i + 2)];
				localStringBuffer.append(var_20c[((k & 0xF) << 1733269922 | l >>> -2016244538)]).append(var_20c[(0x3F & l)]);
				continue;
			}
			localStringBuffer.append(var_20c[((k & 0xF) << 1051789634)]).append("=");
		}

    return localStringBuffer.toString();
  }

  static
  {
    int i = 0;
    for (; 26 > i; ++i) {
      var_20c[i] = (char)(65 - -i);
    }

    i = 26;
    for (; 52 > i; ++i) {
      var_20c[i] = (char)(71 + i);
    }

    i = 52;
    for (; (i ^ 0xFFFFFFFF) > -63; ++i) {
      var_20c[i] = (char)(-52 + (i + 48));
    }

    var_20c[63] = '/';
    var_20c[62] = '+';

    var_214 = new char[64];

    i = 0;
    for (; 26 > i; ++i) {
      var_214[i] = (char)(65 + i);
    }

    i = 26;
    for (; (i ^ 0xFFFFFFFF) > -53; ++i) {
      var_214[i] = (char)(97 - (-i - -26));
    }

    i = 52;
    for (; (i ^ 0xFFFFFFFF) > -63; ++i) {
      var_214[i] = (char)(48 + i + -52);
    }

    var_214[62] = '*';
    var_214[63] = '-';

    var_21c = new int[''];

    i = 0;
    for (; (i ^ 0xFFFFFFFF) > (var_21c.length ^ 0xFFFFFFFF); ++i) {
      var_21c[i] = -1;
    }

    i = 65;
    for (; 90 >= i; ++i) {
      var_21c[i] = (i - 65);
    }

    i = 97;
    for (; i <= 122; ++i) {
      var_21c[i] = (i + -97 - -26);
    }

    i = 48;
    for (; 57 >= i; ++i) {
      var_21c[i] = (-48 + (i - -52));
    }

    var_21c[47] = 63; var_21c[45] = 63;
    var_21c[43] = 62; var_21c[42] = 62;
  }
}

/* Location:           C:\Windows\.jagex_cache_32\jagexlauncher\bin\jagexappletviewer\
 * Qualified Name:     app.Class_k
 * JD-Core Version:    0.5.4
 */