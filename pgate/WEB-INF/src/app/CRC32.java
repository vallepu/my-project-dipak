package app;

//CRC32 calculation.
//This software is in the public domain.
//

/**
 *<P>
 * Calculates the CRC32 - 32 bit Cyclical Redundancy Check
 *<P>
 * This check is used in numerous systems to verify the integrity of
 * information. It's also used as a hashing function. Unlike a regular checksum,
 * it's sensitive to the order of the characters. It produces a 32 bit (Java
 * <CODE>int</CODE>.
 *<P>
 * This Java programme was translated from a C version I had written.
 *<P>
 * This software is in the public domain.
 * 
 *<P>
 * When calculating the CRC32 over a number of strings or byte arrays the
 * previously calculated CRC is passed to the next call. In this way the CRC is
 * built up over a number of items, including a mix of strings and byte arrays.
 *<P>
 * 
 * <PRE>
 * CRC32 crc = new CRC32();
 * int crcCalc = crc.crc32(&quot;Hello World&quot;);
 * crcCalc = crc.crc32(&quot;How are you?&quot;, crcCalc);
 * crcCalc = crc.crc32(&quot;I'm feeling really good, how about you?&quot;, crcCalc);
 *</PRE>
 * 
 * The line<CODE>int crcCalc = crc.crc32("Hello World");</CODE> is equivalent to
 * <CODE>int crcCalc = crc.crc32("Hello World", -1);</CODE>. When starting a new
 * CRC calculation the "previous crc" is set to 0xFFFFFFFF (or -1).
 *<P>
 * The table only needs to be built once. You may use it to generate many CRC's.
 *<CODE>
 * 
 * @author Michael Lecuyer
 * 
 * @version 1.1 August 11, 1998
 * 
 */
public class CRC32 {
	
	int CRCTable[]; // CRC Lookup table

	/**
	 * Tests CRC32. <BR>
	 * Eg:<SAMP> java CRC32 "Howdy, I'm A Cowboy"
	 * 
	 * @param args
	 *            the string used to calculate the CRC32
	 */
	/**
	 * public static void main(String args[]) {
		if (args.length == 0) {
			System.out.println("Usage CRC32 [string to calculate CRC32]");
			System.exit(1);
		}

		System.out.println("CRC for [" + args[0] + "] is " + new CRC32().crc32(args[0]));
	}*/

	private int crc; // currently calculated crc (used in conversion to byte
						// array)

	/**
	 * Constructor constructs the lookup table.
	 * 
	 */
	CRC32() {
		this.buildCRCTable();
	}

	/**
	 * Just build a plain old fashioned table based on good, old fashioned
	 * values like the CRC32_POLYNOMIAL. The table is of a fixed size.
	 */
	private void buildCRCTable() {
		final int CRC32_POLYNOMIAL = 0xEDB88320;

		int i, j;
		int crc;

		this.CRCTable = new int[256];

		for (i = 0; i <= 255; i++) {
			crc = i;
			for (j = 8; j > 0; j--) {
				if ((crc & 1) == 1) {
					crc = (crc >>> 1) ^ CRC32_POLYNOMIAL;
				} else {
					crc >>>= 1;
				}
			}
			this.CRCTable[i] = crc;
		}
	}

	/**
	 * Convenience mithod for generating a CRC from a single<CODE>String</CODE>.
	 * 
	 * @param buffer
	 *            string to generate the CRC32
	 * 
	 * @return 32 bit CRC
	 */
	public int crc32(String buffer) {
		return this.crc32(buffer, 0xFFFFFFFF);
	}

	/**
	 * Convenience method for generating a CRC from a<CODE>byte</CODE> array.
	 * 
	 * @param buffer
	 *            byte array to generate the CRC32
	 * 
	 * @return 32 bit CRC
	 */
	public int crc32(byte buffer[]) {
		return this.crc32(buffer, 0xFFFFFFFF);
	}

	/**
	 * Convenience method for generating a CRC from a series of
	 * <CODE>String</CODE>'s.
	 * 
	 * @param buffer
	 *            string to generate the CRC32
	 * @param crc
	 *            previously generated CRC32.
	 * 
	 * @return 32 bit CRC
	 */
	public int crc32(String buffer, int crc) {
		return this.crc32(buffer.getBytes(), crc);
	}

	/**
	 * Convenience method for generating a CRC from a series of<CODE>byte</CODE>
	 * arrays.
	 * 
	 * @param buffer
	 *            byte array to generate the CRC32
	 * @param crc
	 *            previously generated CRC32.
	 * 
	 * @return 32 bit CRC
	 */
	public int crc32(byte buffer[], int crc) {
		return this.crc32(buffer, 0, buffer.length, crc);
	}

	/**
	 * General CRC generation function.
	 * 
	 * @param buffer
	 *            byte array to generate the CRC32
	 * @param start
	 *            byte start position
	 * @param count
	 *            number of byte's to include in CRC calculation
	 * @param crc
	 *            previously generated CRC32.
	 * 
	 * @return 32 bit CRC
	 */
	public int crc32(byte buffer[], int start, int count, int lastcrc) {
		int temp1, temp2;
		int i = start;

		this.crc = lastcrc;

		while (count-- != 0) {
			temp1 = this.crc >>> 8;
			temp2 = this.CRCTable[(this.crc ^ buffer[i++]) & 0xFF];
			this.crc = temp1 ^ temp2;
		}

		return this.crc;
	}
}
