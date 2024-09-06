// Decompiled by DJ v3.6.6.79 Copyright 2004 Atanas Neshkov  Date: 2012-12-18 ���� 4:58:53
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SHA256.java

package kr.go.rastech.commons.utils;

/**
 * 
 * @author Administrator
 * AS - IS 암호화 방식 유지
 *
 */
public class SHA256
{

    private SHA256()
    {
        c_Count = new int[4];
        b_Buffer = new byte[S_SHA256_DIGEST_BLOCKLEN];
    }

    public SHA256(byte bpMessage[])
    {
        c_Count = new int[4];
        b_Buffer = new byte[S_SHA256_DIGEST_BLOCKLEN];
        sHA256_Update(bpMessage);
        sHA256_Final();
    }

    public byte[] getHashCode()
    {
    	byte[] newB_bpDigest = new byte[b_bpDigest.length];
	    System.arraycopy(b_bpDigest, 0, newB_bpDigest, 0, b_bpDigest.length);
		return newB_bpDigest;
		
       // return b_bpDigest;
    }

    private void sHA256_Update(byte bpMessage[])
    {
        int nMessageLen = bpMessage.length;
        int nMessageIndex = 0;
        if((c_Count[0] += nMessageLen << 3) < 0){
            c_Count[1]++;
        }
        c_Count[1] += nMessageLen >> 29;
        for(; nMessageLen >= S_SHA256_DIGEST_BLOCKLEN; nMessageLen -= S_SHA256_DIGEST_BLOCKLEN)
        {
            for(int i = 0; i < S_SHA256_DIGEST_BLOCKLEN; i++){
               b_Buffer[i] = bpMessage[nMessageIndex + i];
            }

            sHA256_Transform();
            nMessageIndex += S_SHA256_DIGEST_BLOCKLEN;
        }

        for(int i = 0; i < nMessageLen; i++){
            b_Buffer[i] = bpMessage[nMessageIndex + i];
        }

    }

    private void sHA256_Final()
    {
        int nCountL = c_Count[0];
        int nCountH = c_Count[1];
        int nIndex = (nCountL >> 3) % S_SHA256_DIGEST_BLOCKLEN;
        b_Buffer[nIndex++] = -128;
        if(nIndex > S_SHA256_DIGEST_BLOCKLEN - 8)
        {
            for(int i = nIndex; i < S_SHA256_DIGEST_BLOCKLEN; i++){
                b_Buffer[i] = 0;
            }
            sHA256_Transform();
            for(int i = 0; i < S_SHA256_DIGEST_BLOCKLEN - 8; i++){
                b_Buffer[i] = 0;
            }
        } else
        {
            for(int i = nIndex; i < S_SHA256_DIGEST_BLOCKLEN - 8; i++){
                b_Buffer[i] = 0;
            }
        }
        if(e_ENDIAN == l_LITTLE)
        {
            nCountL = eNDIAN_REVERSE_ULONG(nCountL);
            nCountH = eNDIAN_REVERSE_ULONG(nCountH);
        }
        for(int i = 0; i < 4; i++)
        {
            b_Buffer[S_SHA256_DIGEST_BLOCKLEN - 1 - i] = (byte)((nCountL & 255 << 8 * i) >>> 8 * i);
            b_Buffer[S_SHA256_DIGEST_BLOCKLEN - 5 - i] = (byte)((nCountH & 255 << 8 * i) >>> 8 * i);
        }

        sHA256_Transform();
        b_bpDigest = new byte[S_SHA256_DIGEST_VALUELEN];
        for(int i = 0; i < S_SHA256_DIGEST_VALUELEN; i++){
            b_bpDigest[i] = (byte)((c_ChainVar[i / 4] & 0xff000000 >>> 8 * (i % 4)) >>> 32 - 8 * ((i + 1) % 4));
        }
    }

    private void sHA256_Transform()
    {
        int x[] = new int[64];
        for(int j = 0; j < 16; j++)
        {
            int t1 = 0;
            for(int i = 0; i < 4; i++){
                t1 |= (0xff & b_Buffer[i + 4 * j]) << 24 - 8 * i;
            }
            x[j] = getData(t1);
        }

        for(int j = 16; j < 64; j++){
            x[j] = rHO1(x[j - 2]) + x[j - 7] + rHO0(x[j - 15]) + x[j - 16];
        }
        int a = c_ChainVar[0];
        int b = c_ChainVar[1];
        int c = c_ChainVar[2];
        int d = c_ChainVar[3];
        int e = c_ChainVar[4];
        int f = c_ChainVar[5];
        int g = c_ChainVar[6];
        int h = c_ChainVar[7];
        for(int j = 0; j < 64; j += 8)
        {
            int t1 = fF0(e, f, g, h, j + 0, x);
            d += t1;
            h = fF1(t1, a, b, c);
            t1 = fF0(d, e, f, g, j + 1, x);
            c += t1;
            g = fF1(t1, h, a, b);
            t1 = fF0(c, d, e, f, j + 2, x);
            b += t1;
            f = fF1(t1, g, h, a);
            t1 = fF0(b, c, d, e, j + 3, x);
            a += t1;
            e = fF1(t1, f, g, h);
            t1 = fF0(a, b, c, d, j + 4, x);
            h += t1;
            d = fF1(t1, e, f, g);
            t1 = fF0(h, a, b, c, j + 5, x);
            g += t1;
            c = fF1(t1, d, e, f);
            t1 = fF0(g, h, a, b, j + 6, x);
            f += t1;
            b = fF1(t1, c, d, e);
            t1 = fF0(f, g, h, a, j + 7, x);
            e += t1;
            a = fF1(t1, b, c, d);
        }

        c_ChainVar[0] += a;
        c_ChainVar[1] += b;
        c_ChainVar[2] += c;
        c_ChainVar[3] += d;
        c_ChainVar[4] += e;
        c_ChainVar[5] += f;
        c_ChainVar[6] += g;
        c_ChainVar[7] += h;
    }

    private int fF0(int e, int f, int g, int h, int j, int x[])
    {
        return h + sigma1(e) + ch(e, f, g) + k_K[j] + x[j];
    }

    private int fF1(int t1, int a, int b, int c)
    {
        return t1 + sigma0(a) + maj(a, b, c);
    }

    private int ch(int x, int y, int z)
    {
        return x & y ^ ~x & z;
    }

    private int maj(int x, int y, int z)
    {
        return x & y ^ x & z ^ y & z;
    }

    private int sigma0(int x)
    {
        return rR(x, 2) ^ rR(x, 13) ^ rR(x, 22);
    }

    private int sigma1(int x)
    {
        return rR(x, 6) ^ rR(x, 11) ^ rR(x, 25);
    }

    private int getData(int nX)
    {
        if(e_ENDIAN == b_BIG){
            return nX;
        } else{
            return eNDIAN_REVERSE_ULONG(nX);
        }
    }

    private int eNDIAN_REVERSE_ULONG(int nX)
    {
        return rOTL_ULONG(nX, 8) & 0xff00ff | rOTL_ULONG(nX, 24) & 0xff00ff00;
    }

    private int rOTL_ULONG(int x, int n)
    {
        return x << n | x >>> 32 - n;
    }

    private int rOTR_ULONG(int x, int n)
    {
        return x >>> n | x << 32 - n;
    }

    private int rR(int x, int n)
    {
        return rOTR_ULONG(x, n);
    }

    private int sS(int x, int n)
    {
        return x >>> n;
    }

    private int rHO0(int x)
    {
        return rR(x, 7) ^ rR(x, 18) ^ sS(x, 3);
    }

    private int rHO1(int x)
    {
        return rR(x, 17) ^ rR(x, 19) ^ sS(x, 10);
    }

    private static Boolean l_LITTLE = Boolean.valueOf(false);
    private static Boolean b_BIG;
    private static Boolean e_ENDIAN;
    private static final int S_SHA256_DIGEST_BLOCKLEN = 64;
    private static final int S_SHA256_DIGEST_VALUELEN = 32;
    private static int k_K[] = {
        0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5, 0xd807aa98, 0x12835b01, 
        0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174, 0xe49b69c1, 0xefbe4786, 0xfc19dc6, 0x240ca1cc, 
        0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 
        0x6ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85, 
        0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070, 0x19a4c116, 0x1e376c08, 
        0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 
        0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };
    private int c_ChainVar[] = {
        0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19
    };
    private int c_Count[];
    private byte b_Buffer[];
    private byte b_bpDigest[];

    static 
    {
        b_BIG = Boolean.valueOf(true);
        e_ENDIAN = b_BIG;
    }
}