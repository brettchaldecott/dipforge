/*
 * CoadunationRDFResources: The rdf resource object mappings.
 * Copyright (C) 2009  2015 Burntjam
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * CountryLookup.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.base;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.exception.ObjException;

/**
 * The country manager that is responsible for providing a reference to an object
 * for a spec
 * 
 * @author brett chaldecott
 */
public class CountryLookup {
    
    /**
     * This method is responsible for performing the lookup for the appropriate
     * country code object.
     * @param code The country code to be matched.
     * @return The country reference.
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public static Country getCountry(String code) throws ObjException {
        if (code.equals("AD")) {
            return Country.AD;
        }
        else if (code.equals("AE")) {
            return Country.AE;
        }
        else if (code.equals("AF")) {
            return Country.AF;
        }
        else if (code.equals("AG")) {
            return Country.AG;
        }
        else if (code.equals("AI")) {
            return Country.AI;
        }
        else if (code.equals("AL")) {
            return Country.AL;
        }
        else if (code.equals("AM")) {
            return Country.AM;
        }
        else if (code.equals("AN")) {
            return Country.AN;
        }
        else if (code.equals("AO")) {
            return Country.AO;
        }
        else if (code.equals("AQ")) {
            return Country.AQ;
        }
        else if (code.equals("AR")) {
            return Country.AR;
        }
        else if (code.equals("AS")) {
            return Country.AS;
        }
        else if (code.equals("AT")) {
            return Country.AT;
        }
        else if (code.equals("AU")) {
            return Country.AU;
        }
        else if (code.equals("AW")) {
            return Country.AW;
        }
        else if (code.equals("AZ")) {
            return Country.AZ;
        }
        else if (code.equals("BA")) {
            return Country.BA;
        }
        else if (code.equals("BB")) {
            return Country.BB;
        }
        else if (code.equals("BB")) {
            return Country.BD;
        }
        else if (code.equals("BE")) {
            return Country.BE;
        }
        else if (code.equals("BF")) {
            return Country.BF;
        }
        else if (code.equals("BG")) {
            return Country.BG;
        }
        else if (code.equals("BG")) {
            return Country.BH;
        }
        else if (code.equals("BI")) {
            return Country.BI;
        }
        else if (code.equals("BJ")) {
            return Country.BJ;
        }
        else if (code.equals("BM")) {
            return Country.BM;
        }
        else if (code.equals("BN")) {
            return Country.BN;
        }
        else if (code.equals("BO")) {
            return Country.BO;
        }
        else if (code.equals("BR")) {
            return Country.BR;
        }
        else if (code.equals("BS")) {
            return Country.BS;
        }
        else if (code.equals("BT")) {
            return Country.BT;
        }
        else if (code.equals("BU")) {
            return Country.BU;
        }
        else if (code.equals("BV")) {
            return Country.BV;
        }
        else if (code.equals("BW")) {
            return Country.BW;
        }
        else if (code.equals("BY")) {
            return Country.BY;
        }
        else if (code.equals("BZ")) {
            return Country.BZ;
        }
        else if (code.equals("CA")) {
            return Country.CA;
        }
        else if (code.equals("CC")) {
            return Country.CC;
        }
        else if (code.equals("CC")) {
            return Country.CF;
        }
        else if (code.equals("CG")) {
            return Country.CG;
        }
        else if (code.equals("CH")) {
            return Country.CH;
        }
        else if (code.equals("CI")) {
            return Country.CI;
        }
        else if (code.equals("CK")) {
            return Country.CK;
        }
        else if (code.equals("CL")) {
            return Country.CL;
        }
        else if (code.equals("CM")) {
            return Country.CM;
        }
        else if (code.equals("CN")) {
            return Country.CN;
        }
        else if (code.equals("CO")) {
            return Country.CO;
        }
        else if (code.equals("CR")) {
            return Country.CR;
        }
        else if (code.equals("CR")) {
            return Country.CR;
        }
        else if (code.equals("CU")) {
            return Country.CU;
        }
        else if (code.equals("CV")) {
            return Country.CV;
        }
        else if (code.equals("CX")) {
            return Country.CX;
        }
        else if (code.equals("CY")) {
            return Country.CY;
        }
        else if (code.equals("CZ")) {
            return Country.CZ;
        }
        else if (code.equals("DD")) {
            return Country.DD;
        }
        else if (code.equals("DE")) {
            return Country.DE;
        }
        else if (code.equals("DJ")) {
            return Country.DJ;
        }
        else if (code.equals("DK")) {
            return Country.DK;
        }
        else if (code.equals("DM")) {
            return Country.DM;
        }
        else if (code.equals("DO")) {
            return Country.DO;
        }
        else if (code.equals("DZ")) {
            return Country.DZ;
        }
        else if (code.equals("EC")) {
            return Country.EC;
        }
        else if (code.equals("EC")) {
            return Country.EE;
        }
        else if (code.equals("EG")) {
            return Country.EG;
        }
        else if (code.equals("EH")) {
            return Country.EH;
        }
        else if (code.equals("ER")) {
            return Country.ER;
        }
        else if (code.equals("ER")) {
            return Country.ES;
        }
        else if (code.equals("ET")) {
            return Country.ET;
        }
        else if (code.equals("FI")) {
            return Country.FI;
        }
        else if (code.equals("FJ")) {
            return Country.FJ;
        }
        else if (code.equals("FK")) {
            return Country.FK;
        }
        else if (code.equals("FK")) {
            return Country.FK;
        }
        else if (code.equals("FO")) {
            return Country.FO;
        }
        else if (code.equals("FR")) {
            return Country.FR;
        }
        else if (code.equals("FX")) {
            return Country.FX;
        }
        else if (code.equals("GA")) {
            return Country.GA;
        }
        else if (code.equals("GB")) {
            return Country.GB;
        }
        else if (code.equals("GD")) {
            return Country.GD;
        }
        else if (code.equals("GE")) {
            return Country.GE;
        }
        else if (code.equals("GF")) {
            return Country.GF;
        }
        else if (code.equals("GH")) {
            return Country.GH;
        }
        else if (code.equals("GI")) {
            return Country.GI;
        }
        else if (code.equals("GL")) {
            return Country.GL;
        }
        else if (code.equals("GM")) {
            return Country.GM;
        }
        else if (code.equals("GN")) {
            return Country.GN;
        }
        else if (code.equals("GP")) {
            return Country.GP;
        }
        else if (code.equals("GQ")) {
            return Country.GQ;
        }
        else if (code.equals("GR")) {
            return Country.GR;
        }
        else if (code.equals("GS")) {
            return Country.GS;
        }
        else if (code.equals("GT")) {
            return Country.GT;
        }
        else if (code.equals("GU")) {
            return Country.GU;
        }
        else if (code.equals("GW")) {
            return Country.GW;
        }
        else if (code.equals("GY")) {
            return Country.GY;
        }
        else if (code.equals("HK")) {
            return Country.HK;
        }
        else if (code.equals("HM")) {
            return Country.HM;
        }
        else if (code.equals("HN")) {
            return Country.HN;
        }
        else if (code.equals("HR")) {
            return Country.HR;
        }
        else if (code.equals("HT")) {
            return Country.HT;
        }
        else if (code.equals("HU")) {
            return Country.HU;
        }
        else if (code.equals("ID")) {
            return Country.ID;
        }
        else if (code.equals("IE")) {
            return Country.IE;
        }
        else if (code.equals("IL")) {
            return Country.IL;
        }
        else if (code.equals("IN")) {
            return Country.IN;
        }
        else if (code.equals("IO")) {
            return Country.IO;
        }
        else if (code.equals("IQ")) {
            return Country.IQ;
        }
        else if (code.equals("IR")) {
            return Country.IR;
        }
        else if (code.equals("IS")) {
            return Country.IS;
        }
        else if (code.equals("IT")) {
            return Country.IT;
        }
        else if (code.equals("JM")) {
            return Country.JM;
        }
        else if (code.equals("JO")) {
            return Country.JO;
        }
        else if (code.equals("JP")) {
            return Country.JP;
        }
        else if (code.equals("KE")) {
            return Country.KE;
        }
        else if (code.equals("KG")) {
            return Country.KG;
        }
        else if (code.equals("KH")) {
            return Country.KH;
        }
        else if (code.equals("KI")) {
            return Country.KI;
        }
        else if (code.equals("KM")) {
            return Country.KM;
        }
        else if (code.equals("KN")) {
            return Country.KN;
        }
        else if (code.equals("KP")) {
            return Country.KP;
        }
        else if (code.equals("KR")) {
            return Country.KR;
        }
        else if (code.equals("KW")) {
            return Country.KW;
        }
        else if (code.equals("KY")) {
            return Country.KY;
        }
        else if (code.equals("KZ")) {
            return Country.KZ;
        }
        else if (code.equals("LA")) {
            return Country.LA;
        }
        else if (code.equals("LB")) {
            return Country.LB;
        }
        else if (code.equals("LC")) {
            return Country.LC;
        }
        else if (code.equals("LI")) {
            return Country.LI;
        }
        else if (code.equals("LK")) {
            return Country.LK;
        }
        else if (code.equals("LR")) {
            return Country.LR;
        }
        else if (code.equals("LS")) {
            return Country.LS;
        }
        else if (code.equals("LT")) {
            return Country.LT;
        }
        else if (code.equals("LU")) {
            return Country.LU;
        }
        else if (code.equals("LV")) {
            return Country.LV;
        }
        else if (code.equals("LY")) {
            return Country.LY;
        }
        else if (code.equals("MA")) {
            return Country.MA;
        }
        else if (code.equals("MC")) {
            return Country.MC;
        }
        else if (code.equals("MC")) {
            return Country.MC;
        }
        else if (code.equals("MG")) {
            return Country.MG;
        }
        else if (code.equals("MH")) {
            return Country.MH;
        }
        else if (code.equals("ML")) {
            return Country.ML;
        }
        else if (code.equals("MN")) {
            return Country.MN;
        }
        else if (code.equals("MM")) {
            return Country.MM;
        }
        else if (code.equals("MO")) {
            return Country.MO;
        }
        else if (code.equals("MP")) {
            return Country.MP;
        }
        else if (code.equals("MQ")) {
            return Country.MQ;
        }
        else if (code.equals("MR")) {
            return Country.MR;
        }
        else if (code.equals("MS")) {
            return Country.MS;
        }
        else if (code.equals("MT")) {
            return Country.MT;
        }
        else if (code.equals("MU")) {
            return Country.MU;
        }
        else if (code.equals("MV")) {
            return Country.MV;
        }
        else if (code.equals("MW")) {
            return Country.MW;
        }
        else if (code.equals("MW")) {
            return Country.MX;
        }
        else if (code.equals("MY")) {
            return Country.MY;
        }
        else if (code.equals("MZ")) {
            return Country.MZ;
        }
        else if (code.equals("NA")) {
            return Country.NA;
        }
        else if (code.equals("NC")) {
            return Country.NC;
        }
        else if (code.equals("NE")) {
            return Country.NE;
        }
        else if (code.equals("NF")) {
            return Country.NF;
        }
        else if (code.equals("NG")) {
            return Country.NG;
        }
        else if (code.equals("NI")) {
            return Country.NI;
        }
        else if (code.equals("NL")) {
            return Country.NL;
        }
        else if (code.equals("NO")) {
            return Country.NO;
        }
        else if (code.equals("NP")) {
            return Country.NP;
        }
        else if (code.equals("NR")) {
            return Country.NR;
        }
        else if (code.equals("NT")) {
            return Country.NT;
        }
        else if (code.equals("NU")) {
            return Country.NU;
        }
        else if (code.equals("NZ")) {
            return Country.NZ;
        }
        else if (code.equals("OM")) {
            return Country.OM;
        }
        else if (code.equals("PA")) {
            return Country.PA;
        }
        else if (code.equals("PE")) {
            return Country.PE;
        }
        else if (code.equals("PF")) {
            return Country.PF;
        }
        else if (code.equals("PG")) {
            return Country.PG;
        }
        else if (code.equals("PH")) {
            return Country.PH;
        }
        else if (code.equals("PK")) {
            return Country.PK;
        }
        else if (code.equals("PL")) {
            return Country.PL;
        }
        else if (code.equals("PM")) {
            return Country.PM;
        }
        else if (code.equals("PN")) {
            return Country.PN;
        }
        else if (code.equals("PR")) {
            return Country.PR;
        }
        else if (code.equals("PT")) {
            return Country.PT;
        }
        else if (code.equals("PW")) {
            return Country.PW;
        }
        else if (code.equals("PY")) {
            return Country.PY;
        }
        else if (code.equals("QA")) {
            return Country.QA;
        }
        else if (code.equals("RE")) {
            return Country.RE;
        }
        else if (code.equals("RO")) {
            return Country.RO;
        }
        else if (code.equals("RU")) {
            return Country.RU;
        }
        else if (code.equals("RW")) {
            return Country.RW;
        }
        else if (code.equals("SA")) {
            return Country.SA;
        }
        else if (code.equals("SB")) {
            return Country.SB;
        }
        else if (code.equals("SC")) {
            return Country.SC;
        }
        else if (code.equals("SD")) {
            return Country.SD;
        }
        else if (code.equals("SE")) {
            return Country.SE;
        }
        else if (code.equals("SG")) {
            return Country.SG;
        }
        else if (code.equals("SH")) {
            return Country.SH;
        }
        else if (code.equals("SI")) {
            return Country.SI;
        }
        else if (code.equals("SJ")) {
            return Country.SJ;
        }
        else if (code.equals("SK")) {
            return Country.SK;
        }
        else if (code.equals("SL")) {
            return Country.SL;
        }
        else if (code.equals("SM")) {
            return Country.SM;
        }
        else if (code.equals("SN")) {
            return Country.SN;
        }
        else if (code.equals("SH")) {
            return Country.SO;
        }
        else if (code.equals("SR")) {
            return Country.SR;
        }
        else if (code.equals("ST")) {
            return Country.ST;
        }
        else if (code.equals("SU")) {
            return Country.SU;
        }
        else if (code.equals("SV")) {
            return Country.SV;
        }
        else if (code.equals("SY")) {
            return Country.SY;
        }
        else if (code.equals("SZ")) {
            return Country.SZ;
        }
        else if (code.equals("TC")) {
            return Country.TC;
        }
        else if (code.equals("TD")) {
            return Country.TD;
        }
        else if (code.equals("TF")) {
            return Country.TF;
        }
        else if (code.equals("TG")) {
            return Country.TG;
        }
        else if (code.equals("TH")) {
            return Country.TH;
        }
        else if (code.equals("TJ")) {
            return Country.TJ;
        }
        else if (code.equals("TK")) {
            return Country.TK;
        }
        else if (code.equals("TM")) {
            return Country.TM;
        }
        else if (code.equals("TN")) {
            return Country.TN;
        }
        else if (code.equals("TO")) {
            return Country.TO;
        }
        else if (code.equals("TP")) {
            return Country.TP;
        }
        else if (code.equals("TR")) {
            return Country.TR;
        }
        else if (code.equals("TT")) {
            return Country.TT;
        }
        else if (code.equals("TT")) {
            return Country.TT;
        }
        else if (code.equals("TT")) {
            return Country.TW;
        }
        else if (code.equals("TT")) {
            return Country.TT;
        }
        else if (code.equals("UA")) {
            return Country.UA;
        }
        else if (code.equals("UG")) {
            return Country.UG;
        }
        else if (code.equals("UK")) {
            return Country.UK;
        }
        else if (code.equals("UM")) {
            return Country.UM;
        }
        else if (code.equals("US")) {
            return Country.US;
        }
        else if (code.equals("UY")) {
            return Country.UY;
        }
        else if (code.equals("UZ")) {
            return Country.UZ;
        }
        else if (code.equals("VA")) {
            return Country.VA;
        }
        else if (code.equals("VC")) {
            return Country.VC;
        }
        else if (code.equals("VE")) {
            return Country.VE;
        }
        else if (code.equals("VG")) {
            return Country.VG;
        }
        else if (code.equals("VI")) {
            return Country.VI;
        }
        else if (code.equals("VN")) {
            return Country.VN;
        }
        else if (code.equals("VU")) {
            return Country.VU;
        }
        else if (code.equals("WF")) {
            return Country.WF;
        }
        else if (code.equals("WS")) {
            return Country.WS;
        }
        else if (code.equals("YD")) {
            return Country.YD;
        }
        else if (code.equals("YE")) {
            return Country.YE;
        }
        else if (code.equals("YT")) {
            return Country.YT;
        }
        else if (code.equals("YU")) {
            return Country.YU;
        }
        else if (code.equals("ZA")) {
            return Country.ZA;
        }
        else if (code.equals("ZM")) {
            return Country.ZM;
        }
        else if (code.equals("ZR")) {
            return Country.ZR;
        }
        else if (code.equals("ZW")) {
            return Country.ZW;
        }
        else if (code.equals("ZZ")) {
            return Country.ZZ;
        }
        throw new ObjException("Unrecognised country code");
    }
    
}
