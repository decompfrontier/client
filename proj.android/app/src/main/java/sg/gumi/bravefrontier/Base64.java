package sg.gumi.bravefrontier;

public class Base64 {
    final private static char[] CA;
    final private static int[] IA;
    
    static {
        CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        int[] a = new int[256];
        IA = a;
        java.util.Arrays.fill(a, -1);
        int i = CA.length;
        int i0 = 0;
        for(; i0 < i; i0 = i0 + 1) {
            IA[(int)CA[i0]] = i0;
        }
        IA[61] = 0;
    }
    
    public Base64() {
    }
    
    final public static byte[] decode(String s) {
        int i = (s == null) ? 0 : s.length();
        if (i == 0) {
            return new byte[0];
        }
        int i0 = 0;
        int i1 = 0;
        for(; i0 < i; i0 = i0 + 1) {
            if (IA[(int)s.charAt(i0)] < 0) {
                i1 = i1 + 1;
            }
        }
        int i2 = i - i1;
        if (i2 % 4 != 0) {
            byte[] a = null;
            return a;
        }
        int i3 = 0;
        label0: while(true) {
            if (i > 1) {
                int[] a0 = IA;
                i = i - 1;
                if (a0[(int)s.charAt(i)] <= 0) {
                    if ((int)s.charAt(i) != 61) {
                        continue label0;
                    }
                    i3 = i3 + 1;
                    continue label0;
                }
            }
            int i4 = (i2 * 6 >> 3) - i3;
            byte[] a1 = new byte[i4];
            int i5 = 0;
            int i6 = 0;
            while(i5 < i4) {
                int i7 = 0;
                int i8 = 0;
                while(i7 < 4) {
                    int i9 = IA[(int)s.charAt(i6)];
                    if (i9 < 0) {
                        i7 = i7 + -1;
                    } else {
                        i8 = i8 | i9 << 18 - i7 * 6;
                    }
                    i7 = i7 + 1;
                    i6 = i6 + 1;
                }
                int i10 = i5 + 1;
                a1[i5] = (byte)(int)(byte)(int)(byte)(i8 >> 16);
                {
                    if (i10 < i4) {
                        i5 = i10 + 1;
                        a1[i10] = (byte)(int)(byte)(int)(byte)(i8 >> 8);
                        if (i5 >= i4) {
                            continue;
                        }
                        i10 = i5 + 1;
                        a1[i5] = (byte)(int)(byte)(int)(byte)i8;
                    }
                    i5 = i10;
                }
            }
            return a1;
        }
    }
    
    final public static byte[] decode(byte[] a) {
        int i = a.length;
        int i0 = 0;
        int i1 = 0;
        for(; i0 < i; i0 = i0 + 1) {
            if (IA[(int)a[i0] & 255] < 0) {
                i1 = i1 + 1;
            }
        }
        int i2 = i - i1;
        if (i2 % 4 != 0) {
            byte[] a0 = null;
            return a0;
        }
        int i3 = 0;
        label0: while(true) {
            if (i > 1) {
                int[] a1 = IA;
                i = i - 1;
                if (a1[(int)a[i] & 255] <= 0) {
                    if ((int)a[i] != 61) {
                        continue label0;
                    }
                    i3 = i3 + 1;
                    continue label0;
                }
            }
            int i4 = (i2 * 6 >> 3) - i3;
            byte[] a2 = new byte[i4];
            int i5 = 0;
            int i6 = 0;
            while(i5 < i4) {
                int i7 = 0;
                int i8 = 0;
                while(i8 < 4) {
                    int i9 = IA[(int)a[i6] & 255];
                    if (i9 < 0) {
                        i8 = i8 + -1;
                    } else {
                        i7 = i7 | i9 << 18 - i8 * 6;
                    }
                    i8 = i8 + 1;
                    i6 = i6 + 1;
                }
                int i10 = i5 + 1;
                a2[i5] = (byte)(int)(byte)(int)(byte)(i7 >> 16);
                {
                    if (i10 < i4) {
                        i5 = i10 + 1;
                        a2[i10] = (byte)(int)(byte)(int)(byte)(i7 >> 8);
                        if (i5 >= i4) {
                            continue;
                        }
                        i10 = i5 + 1;
                        a2[i5] = (byte)(int)(byte)(int)(byte)i7;
                    }
                    i5 = i10;
                }
            }
            return a2;
        }
    }
    
    final public static byte[] decode(char[] a) {
        int i = (a == null) ? 0 : a.length;
        if (i == 0) {
            return new byte[0];
        }
        int i0 = 0;
        int i1 = 0;
        for(; i0 < i; i0 = i0 + 1) {
            if (IA[(int)a[i0]] < 0) {
                i1 = i1 + 1;
            }
        }
        int i2 = i - i1;
        if (i2 % 4 != 0) {
            byte[] a0 = null;
            return a0;
        }
        int i3 = 0;
        label0: while(true) {
            if (i > 1) {
                int[] a1 = IA;
                i = i - 1;
                if (a1[(int)a[i]] <= 0) {
                    if ((int)a[i] != 61) {
                        continue label0;
                    }
                    i3 = i3 + 1;
                    continue label0;
                }
            }
            int i4 = (i2 * 6 >> 3) - i3;
            byte[] a2 = new byte[i4];
            int i5 = 0;
            int i6 = 0;
            while(i5 < i4) {
                int i7 = 0;
                int i8 = 0;
                while(i7 < 4) {
                    int i9 = IA[(int)a[i6]];
                    if (i9 < 0) {
                        i7 = i7 + -1;
                    } else {
                        i8 = i8 | i9 << 18 - i7 * 6;
                    }
                    i7 = i7 + 1;
                    i6 = i6 + 1;
                }
                int i10 = i5 + 1;
                a2[i5] = (byte)(int)(byte)(int)(byte)(i8 >> 16);
                {
                    if (i10 < i4) {
                        i5 = i10 + 1;
                        a2[i10] = (byte)(int)(byte)(int)(byte)(i8 >> 8);
                        if (i5 >= i4) {
                            continue;
                        }
                        i10 = i5 + 1;
                        a2[i5] = (byte)(int)(byte)(int)(byte)i8;
                    }
                    i5 = i10;
                }
            }
            return a2;
        }
    }
    
    final public static byte[] decodeFast(String s) {
        int i = s.length();
        if (i == 0) {
            return new byte[0];
        }
        int i0 = i - 1;
        int i1 = 0;
        label3: while(true) {
            if (i1 < i0 && IA[(int)s.charAt(i1) & 255] < 0) {
                i1 = i1 + 1;
                continue label3;
            }
            label2: while(true) {
                if (i0 > 0 && IA[(int)s.charAt(i0) & 255] < 0) {
                    i0 = i0 + -1;
                    continue label2;
                }
                int i2 = ((int)s.charAt(i0) != 61) ? 0 : ((int)s.charAt(i0 - 1) != 61) ? 1 : 2;
                int i3 = i0 - i1 + 1;
                int i4 = (i <= 76) ? 0 : (((int)s.charAt(76) != 13) ? 0 : i3 / 78) << 1;
                int i5 = ((i3 - i4) * 6 >> 3) - i2;
                byte[] a = new byte[i5];
                int i6 = i5 / 3;
                int i7 = 0;
                int i8 = 0;
                while(i7 < i6 * 3) {
                    int[] a0 = IA;
                    int i9 = i1 + 1;
                    int i10 = a0[(int)s.charAt(i1)];
                    int[] a1 = IA;
                    int i11 = i9 + 1;
                    int i12 = a1[(int)s.charAt(i9)];
                    int[] a2 = IA;
                    int i13 = i11 + 1;
                    int i14 = a2[(int)s.charAt(i11)];
                    int[] a3 = IA;
                    i1 = i13 + 1;
                    int i15 = i10 << 18 | i12 << 12 | i14 << 6 | a3[(int)s.charAt(i13)];
                    int i16 = i7 + 1;
                    a[i7] = (byte)(int)(byte)(int)(byte)(i15 >> 16);
                    int i17 = i16 + 1;
                    a[i16] = (byte)(int)(byte)(int)(byte)(i15 >> 8);
                    a[i17] = (byte)(int)(byte)(int)(byte)i15;
                    label1: {
                        label0: {
                            {
                                if (i4 <= 0) {
                                    break label1;
                                }
                                i8 = i8 + 1;
                                if (i8 == 19) {
                                    break label0;
                                }
                            }
                            break label1;
                        }
                        i1 = i1 + 2;
                        i8 = 0;
                    }
                    i7 = i17 + 1;
                }
                if (i7 < i5) {
                    int i18 = 0;
                    int i19 = 0;
                    for(; i1 <= i0 - i2; i1 = i1 + 1) {
                        i18 = i18 | IA[(int)s.charAt(i1)] << 18 - i19 * 6;
                        i19 = i19 + 1;
                    }
                    int i20 = 16;
                    for(; i7 < i5; i7 = i7 + 1) {
                        a[i7] = (byte)(int)(byte)(int)(byte)(i18 >> i20);
                        i20 = i20 + -8;
                    }
                }
                return a;
            }
        }
    }
    
    final public static byte[] decodeFast(byte[] a) {
        int i = a.length;
        if (i == 0) {
            return new byte[0];
        }
        int i0 = i - 1;
        int i1 = 0;
        label3: while(true) {
            if (i1 < i0 && IA[(int)a[i1] & 255] < 0) {
                i1 = i1 + 1;
                continue label3;
            }
            label2: while(true) {
                if (i0 > 0 && IA[(int)a[i0] & 255] < 0) {
                    i0 = i0 + -1;
                    continue label2;
                }
                int i2 = ((int)a[i0] != 61) ? 0 : ((int)a[i0 - 1] != 61) ? 1 : 2;
                int i3 = i0 - i1 + 1;
                int i4 = (i <= 76) ? 0 : (((int)a[76] != 13) ? 0 : i3 / 78) << 1;
                int i5 = ((i3 - i4) * 6 >> 3) - i2;
                byte[] a0 = new byte[i5];
                int i6 = i5 / 3;
                int i7 = 0;
                int i8 = 0;
                while(i7 < i6 * 3) {
                    int[] a1 = IA;
                    int i9 = i1 + 1;
                    int i10 = a1[(int)a[i1]];
                    int i11 = i9 + 1;
                    int i12 = a1[(int)a[i9]];
                    int i13 = i11 + 1;
                    int i14 = a1[(int)a[i11]];
                    i1 = i13 + 1;
                    int i15 = i10 << 18 | i12 << 12 | i14 << 6 | a1[(int)a[i13]];
                    int i16 = i7 + 1;
                    a0[i7] = (byte)(int)(byte)(int)(byte)(i15 >> 16);
                    int i17 = i16 + 1;
                    a0[i16] = (byte)(int)(byte)(int)(byte)(i15 >> 8);
                    a0[i17] = (byte)(int)(byte)(int)(byte)i15;
                    label1: {
                        label0: {
                            {
                                if (i4 <= 0) {
                                    break label1;
                                }
                                i8 = i8 + 1;
                                if (i8 == 19) {
                                    break label0;
                                }
                            }
                            break label1;
                        }
                        i1 = i1 + 2;
                        i8 = 0;
                    }
                    i7 = i17 + 1;
                }
                if (i7 < i5) {
                    int i18 = 0;
                    int i19 = 0;
                    for(; i1 <= i0 - i2; i1 = i1 + 1) {
                        i18 = i18 | IA[(int)a[i1]] << 18 - i19 * 6;
                        i19 = i19 + 1;
                    }
                    int i20 = 16;
                    for(; i7 < i5; i7 = i7 + 1) {
                        a0[i7] = (byte)(int)(byte)(int)(byte)(i18 >> i20);
                        i20 = i20 + -8;
                    }
                }
                return a0;
            }
        }
    }
    
    final public static byte[] decodeFast(char[] a) {
        int i = a.length;
        if (i == 0) {
            return new byte[0];
        }
        int i0 = i - 1;
        int i1 = 0;
        label3: while(true) {
            if (i1 < i0 && IA[(int)a[i1]] < 0) {
                i1 = i1 + 1;
                continue label3;
            }
            label2: while(true) {
                if (i0 > 0 && IA[(int)a[i0]] < 0) {
                    i0 = i0 + -1;
                    continue label2;
                }
                int i2 = ((int)a[i0] != 61) ? 0 : ((int)a[i0 - 1] != 61) ? 1 : 2;
                int i3 = i0 - i1 + 1;
                int i4 = (i <= 76) ? 0 : (((int)a[76] != 13) ? 0 : i3 / 78) << 1;
                int i5 = ((i3 - i4) * 6 >> 3) - i2;
                byte[] a0 = new byte[i5];
                int i6 = i5 / 3;
                int i7 = 0;
                int i8 = 0;
                while(i7 < i6 * 3) {
                    int[] a1 = IA;
                    int i9 = i1 + 1;
                    int i10 = a1[(int)a[i1]];
                    int i11 = i9 + 1;
                    int i12 = a1[(int)a[i9]];
                    int i13 = i11 + 1;
                    int i14 = a1[(int)a[i11]];
                    i1 = i13 + 1;
                    int i15 = i10 << 18 | i12 << 12 | i14 << 6 | a1[(int)a[i13]];
                    int i16 = i7 + 1;
                    a0[i7] = (byte)(int)(byte)(int)(byte)(i15 >> 16);
                    int i17 = i16 + 1;
                    a0[i16] = (byte)(int)(byte)(int)(byte)(i15 >> 8);
                    a0[i17] = (byte)(int)(byte)(int)(byte)i15;
                    label1: {
                        label0: {
                            {
                                if (i4 <= 0) {
                                    break label1;
                                }
                                i8 = i8 + 1;
                                if (i8 == 19) {
                                    break label0;
                                }
                            }
                            break label1;
                        }
                        i1 = i1 + 2;
                        i8 = 0;
                    }
                    i7 = i17 + 1;
                }
                if (i7 < i5) {
                    int i18 = 0;
                    int i19 = 0;
                    for(; i1 <= i0 - i2; i1 = i1 + 1) {
                        i18 = i18 | IA[(int)a[i1]] << 18 - i19 * 6;
                        i19 = i19 + 1;
                    }
                    int i20 = 16;
                    for(; i7 < i5; i7 = i7 + 1) {
                        a0[i7] = (byte)(int)(byte)(int)(byte)(i18 >> i20);
                        i20 = i20 + -8;
                    }
                }
                return a0;
            }
        }
    }
    
    final public static byte[] encodeToByte(byte[] a, boolean b) {
        int i = (a == null) ? 0 : a.length;
        if (i == 0) {
            return new byte[0];
        }
        int i0 = i / 3 * 3;
        int i1 = i - 1;
        int i2 = i1 / 3 + 1 << 2;
        int i3 = i2 + (b ? (i2 - 1) / 76 << 1 : 0);
        byte[] a0 = new byte[i3];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while(i5 < i0) {
            int i7 = i5 + 1;
            int i8 = a[i5];
            int i9 = i7 + 1;
            int i10 = (i8 & 255) << 16 | ((int)a[i7] & 255) << 8 | (int)a[i9] & 255;
            int i11 = i4 + 1;
            char[] a1 = CA;
            a0[i4] = (byte)(int)(byte)(int)(byte)(int)a1[i10 >>> 18 & 63];
            int i12 = i11 + 1;
            a0[i11] = (byte)(int)(byte)(int)(byte)(int)a1[i10 >>> 12 & 63];
            int i13 = i12 + 1;
            a0[i12] = (byte)(int)(byte)(int)(byte)(int)a1[i10 >>> 6 & 63];
            i4 = i13 + 1;
            a0[i13] = (byte)(int)(byte)(int)(byte)(int)a1[i10 & 63];
            if (b) {
                i6 = i6 + 1;
                if (i6 == 19 && i4 < i3 - 2) {
                    int i14 = i4 + 1;
                    a0[i4] = (byte)13;
                    i4 = i14 + 1;
                    a0[i14] = (byte)10;
                    i6 = 0;
                }
            }
            i5 = i9 + 1;
        }
        int i15 = i - i0;
        if (i15 > 0) {
            int i16 = ((int)a[i0] & 255) << 10 | ((i15 != 2) ? 0 : ((int)a[i1] & 255) << 2);
            char[] a2 = CA;
            a0[i3 - 4] = (byte)(int)(byte)(int)(byte)(int)a2[i16 >> 12];
            a0[i3 - 3] = (byte)(int)(byte)(int)(byte)(int)a2[i16 >>> 6 & 63];
            a0[i3 - 2] = (byte)(int)(byte)((i15 != 2) ? 61 : (byte)(int)a2[i16 & 63]);
            a0[i3 - 1] = (byte)61;
        }
        return a0;
    }
    
    final public static char[] encodeToChar(byte[] a, boolean b) {
        int i = (a == null) ? 0 : a.length;
        if (i == 0) {
            return new char[0];
        }
        int i0 = i / 3 * 3;
        int i1 = i - 1;
        int i2 = i1 / 3 + 1 << 2;
        int i3 = i2 + (b ? (i2 - 1) / 76 << 1 : 0);
        char[] a0 = new char[i3];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while(i5 < i0) {
            int i7 = i5 + 1;
            int i8 = a[i5];
            int i9 = i7 + 1;
            int i10 = (i8 & 255) << 16 | ((int)a[i7] & 255) << 8 | (int)a[i9] & 255;
            int i11 = i4 + 1;
            char[] a1 = CA;
            a0[i4] = (char)(int)(char)(int)a1[i10 >>> 18 & 63];
            int i12 = i11 + 1;
            a0[i11] = (char)(int)(char)(int)a1[i10 >>> 12 & 63];
            int i13 = i12 + 1;
            a0[i12] = (char)(int)(char)(int)a1[i10 >>> 6 & 63];
            i4 = i13 + 1;
            a0[i13] = (char)(int)(char)(int)a1[i10 & 63];
            if (b) {
                i6 = i6 + 1;
                if (i6 == 19 && i4 < i3 - 2) {
                    int i14 = i4 + 1;
                    a0[i4] = (char)13;
                    i4 = i14 + 1;
                    a0[i14] = (char)10;
                    i6 = 0;
                }
            }
            i5 = i9 + 1;
        }
        int i15 = i - i0;
        if (i15 > 0) {
            int i16 = ((int)a[i0] & 255) << 10 | ((i15 != 2) ? 0 : ((int)a[i1] & 255) << 2);
            char[] a2 = CA;
            a0[i3 - 4] = (char)(int)(char)(int)a2[i16 >> 12];
            a0[i3 - 3] = (char)(int)(char)(int)a2[i16 >>> 6 & 63];
            a0[i3 - 2] = (char)(int)(char)((i15 != 2) ? 61 : a2[i16 & 63]);
            a0[i3 - 1] = (char)61;
        }
        return a0;
    }
    
    final public static String encodeToString(byte[] a, boolean b) {
        return new String(Base64.encodeToChar(a, b));
    }
}
