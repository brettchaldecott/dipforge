/*
 * CoadunationRDFResources: The rdf resource object mappings.
 * Copyright (C) 2009  Rift IT Contracting
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
 * Country.java
 */

// the package path
package com.rift.coad.rdf.objmapping.base;


// the semantic information
import thewebsemantic.Namespace;
import thewebsemantic.Identifier;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;



/**
 * The object that represents the country information.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("Country")
public class Country extends DataType {
    
    // country codes
    public static Country AD = new Country("AD","Andorra");
    public static Country AE = new Country("AE","United Arab Emirates");
    public static Country AF = new Country("AF","Afghanistan");
    public static Country AG = new Country("AG","Antigua & Barbuda");
    public static Country AI = new Country("AI","Anguilla");
    public static Country AL = new Country("AL","Albania");
    public static Country AM = new Country("AM","Armenia");
    public static Country AN = new Country("AN","Netherlands Antilles");
    public static Country AO = new Country("AO","Angola");
    public static Country AQ = new Country("AQ","Antarctica");
    public static Country AR = new Country("AR","Argentina");
    public static Country AS = new Country("AS","American Samoa");
    public static Country AT = new Country("AT","Austria");
    public static Country AU = new Country("AU","Australia");
    public static Country AW = new Country("AW","Aruba");
    public static Country AZ = new Country("AZ","Azerbaijan");
    public static Country BA = new Country("BA","Bosnia and Herzegovina");
    public static Country BB = new Country("BB","Barbados");
    public static Country BD = new Country("BD","Bangladesh");
    public static Country BE = new Country("BE","Belgium");
    public static Country BF = new Country("BF","Burkina Faso");
    public static Country BG = new Country("BG","Bulgaria");
    public static Country BH = new Country("BH","Bahrain");
    public static Country BI = new Country("BI","Burundi");
    public static Country BJ = new Country("BJ","Benin");
    public static Country BM = new Country("BM","Bermuda");
    public static Country BN = new Country("BN","Brunei Darussalam");
    public static Country BO = new Country("BO","Bolivia");
    public static Country BR = new Country("BR","Brazil");
    public static Country BS = new Country("BS","Bahama");
    public static Country BT = new Country("BT","Bhutan");
    public static Country BU = new Country("BU","Burma (no longer exists)");
    public static Country BV = new Country("BV","Bouvet Island");
    public static Country BW = new Country("BW","Botswana");
    public static Country BY = new Country("BY","Belarus");
    public static Country BZ = new Country("BZ","Belize");
    public static Country CA = new Country("CA","Canada");
    public static Country CC = new Country("CC","Cocos (Keeling) Islands");
    public static Country CF = new Country("CF","Central African Republic");
    public static Country CG = new Country("CG","Congo");
    public static Country CH = new Country("CH","Switzerland");
    public static Country CI = new Country("CI","Côte D'ivoire (Ivory Coast)");
    public static Country CK = new Country("CK","Cook Iislands");
    public static Country CL = new Country("CL","Chile");
    public static Country CM = new Country("CM","Cameroon");
    public static Country CN = new Country("CN","China");
    public static Country CO = new Country("CO","Colombia");
    public static Country CR = new Country("CR","Costa Rica");
    public static Country CS = new Country("CS","Czechoslovakia (no longer exists)");
    public static Country CU = new Country("CU","Cuba");
    public static Country CV = new Country("CV","Cape Verde");
    public static Country CX = new Country("CX","Christmas Island");
    public static Country CY = new Country("CY","Cyprus");
    public static Country CZ = new Country("CZ","Czech Republic");
    public static Country DD = new Country("DD","German Democratic Republic (no longer exists)");
    public static Country DE = new Country("DE","Germany");
    public static Country DJ = new Country("DJ","Djibouti");
    public static Country DK = new Country("DK","Denmark");
    public static Country DM = new Country("DM","Dominica");
    public static Country DO = new Country("DO","Dominican Republic");
    public static Country DZ = new Country("DZ","Algeria");
    public static Country EC = new Country("EC","Ecuador");
    public static Country EE = new Country("EE","Estonia");
    public static Country EG = new Country("EG","Egypt");
    public static Country EH = new Country("EH","Western Sahara");
    public static Country ER = new Country("ER","Eritrea");
    public static Country ES = new Country("ES","Spain");
    public static Country ET = new Country("ET","Ethiopia");
    public static Country FI = new Country("FI","Finland");
    public static Country FJ = new Country("FJ","Fiji");
    public static Country FK = new Country("FK","Falkland Islands (Malvinas)");
    public static Country FM = new Country("FM","Micronesia");
    public static Country FO = new Country("FO","Faroe Islands");
    public static Country FR = new Country("FR","France");
    public static Country FX = new Country("FX","France, Metropolitan");
    public static Country GA = new Country("GA","Gabon");
    public static Country GB = new Country("GB","United Kingdom (Great Britain)");
    public static Country GD = new Country("GD","Grenada");
    public static Country GE = new Country("GE","Georgia");
    public static Country GF = new Country("GF","French Guiana");
    public static Country GH = new Country("GH","Ghana");
    public static Country GI = new Country("GI","Gibraltar");
    public static Country GL = new Country("GL","Greenland");
    public static Country GM = new Country("GM","Gambia");
    public static Country GN = new Country("GN","Guinea");
    public static Country GP = new Country("GP","Guadeloupe");
    public static Country GQ = new Country("GQ","Equatorial Guinea");
    public static Country GR = new Country("GR","Greece");
    public static Country GS = new Country("GS","South Georgia and the South Sandwich Islands");
    public static Country GT = new Country("GT","Guatemala");
    public static Country GU = new Country("GU","Guam");
    public static Country GW = new Country("GW","Guinea-Bissau");
    public static Country GY = new Country("GY","Guyana");
    public static Country HK = new Country("HK","Hong Kong");
    public static Country HM = new Country("HM","Heard & McDonald Islands");
    public static Country HN = new Country("HN","Honduras");
    public static Country HR = new Country("HR","Croatia");
    public static Country HT = new Country("HT","Haiti");
    public static Country HU = new Country("HU","Hungary");
    public static Country ID = new Country("ID","Indonesia");
    public static Country IE = new Country("IE","Ireland");
    public static Country IL = new Country("IL","Israel");
    public static Country IN = new Country("IN","India");
    public static Country IO = new Country("IO","British Indian Ocean Territory");
    public static Country IQ = new Country("IQ","Iraq");
    public static Country IR = new Country("IR","Islamic Republic of Iran");
    public static Country IS = new Country("IS","Iceland");
    public static Country IT = new Country("IT","Italy");
    public static Country JM = new Country("JM","Jamaica");
    public static Country JO = new Country("JO","Jordan");
    public static Country JP = new Country("JP","Japan");
    public static Country KE = new Country("KE","Kenya");
    public static Country KG = new Country("KG","Kyrgyzstan");
    public static Country KH = new Country("KH","Cambodia");
    public static Country KI = new Country("KI","Kiribati");
    public static Country KM = new Country("KM","Comoros");
    public static Country KN = new Country("KN","St. Kitts and Nevis");
    public static Country KP = new Country("KP","Korea, Democratic People's Republic of");
    public static Country KR = new Country("KR","Korea, Republic of");
    public static Country KW = new Country("KW","Kuwait");
    public static Country KY = new Country("KY","Cayman Islands");
    public static Country KZ = new Country("KZ","Kazakhstan");
    public static Country LA = new Country("LA","Lao People's Democratic Republic");
    public static Country LB = new Country("LB","Lebanon");
    public static Country LC = new Country("LC","Saint Lucia");
    public static Country LI = new Country("LI","Liechtenstein");
    public static Country LK = new Country("LK","Sri Lanka");
    public static Country LR = new Country("LR","Liberia");
    public static Country LS = new Country("LS","Lesotho");
    public static Country LT = new Country("LT","Lithuania");
    public static Country LU = new Country("LU","Luxembourg");
    public static Country LV = new Country("LV","Latvia");
    public static Country LY = new Country("LY","Libyan Arab Jamahiriya");
    public static Country MA = new Country("MA","Morocco");
    public static Country MC = new Country("MC","Monaco");
    public static Country MD = new Country("MD","Moldova, Republic of");
    public static Country MG = new Country("MG","Madagascar");
    public static Country MH = new Country("MH","Marshall Islands");
    public static Country ML = new Country("ML","Mali");
    public static Country MN = new Country("MN","Mongolia");
    public static Country MM = new Country("MM","Myanmar");
    public static Country MO = new Country("MO","Macau");
    public static Country MP = new Country("MP","Northern Mariana Islands");
    public static Country MQ = new Country("MQ","Martinique");
    public static Country MR = new Country("MR","Mauritania");
    public static Country MS = new Country("MS","Monserrat");
    public static Country MT = new Country("MT","Malta");
    public static Country MU = new Country("MU","Mauritius");
    public static Country MV = new Country("MV","Maldives");
    public static Country MW = new Country("MW","Malawi");
    public static Country MX = new Country("MX","Mexico");
    public static Country MY = new Country("MY","Malaysia");
    public static Country MZ = new Country("MZ","Mozambique");
    public static Country NA = new Country("NA","Namibia");
    public static Country NC = new Country("NC","New Caledonia");
    public static Country NE = new Country("NE","Niger");
    public static Country NF = new Country("NF","Norfolk Island");
    public static Country NG = new Country("NG","Nigeria");
    public static Country NI = new Country("NI","Nicaragua");
    public static Country NL = new Country("NL","Netherlands");
    public static Country NO = new Country("NO","Norway");
    public static Country NP = new Country("NP","Nepal");
    public static Country NR = new Country("NR","Nauru");
    public static Country NT = new Country("NT","Neutral Zone (no longer exists)");
    public static Country NU = new Country("NU","Niue");
    public static Country NZ = new Country("NZ","New Zealand");
    public static Country OM = new Country("OM","Oman");
    public static Country PA = new Country("PA","Panama");
    public static Country PE = new Country("PE","Peru");
    public static Country PF = new Country("PF","French Polynesia");
    public static Country PG = new Country("PG","Papua New Guinea");
    public static Country PH = new Country("PH","Philippines");
    public static Country PK = new Country("PK","Pakistan");
    public static Country PL = new Country("PL","Poland");
    public static Country PM = new Country("PM","St. Pierre & Miquelon");
    public static Country PN = new Country("PN","Pitcairn");
    public static Country PR = new Country("PR","Puerto Rico");
    public static Country PT = new Country("PT","Portugal");
    public static Country PW = new Country("PW","Palau");
    public static Country PY = new Country("PY","Paraguay");
    public static Country QA = new Country("QA","Qatar");
    public static Country RE = new Country("RE","Réunion");
    public static Country RO = new Country("RO","Romania");
    public static Country RU = new Country("RU","Russian Federation");
    public static Country RW = new Country("RW","Rwanda");
    public static Country SA = new Country("SA","Saudi Arabia");
    public static Country SB = new Country("SB","Solomon Islands");
    public static Country SC = new Country("SC","Seychelles");
    public static Country SD = new Country("SD","Sudan");
    public static Country SE = new Country("SE","Sweden");
    public static Country SG = new Country("SG","Singapore");
    public static Country SH = new Country("SH","St. Helena");
    public static Country SI = new Country("SI","Slovenia");
    public static Country SJ = new Country("SJ","Svalbard & Jan Mayen Islands");
    public static Country SK = new Country("SK","Slovakia");
    public static Country SL = new Country("SL","Sierra Leone");
    public static Country SM = new Country("SM","San Marino");
    public static Country SN = new Country("SN","Senegal");
    public static Country SO = new Country("SO","Somalia");
    public static Country SR = new Country("SR","Suriname");
    public static Country ST = new Country("ST","Sao Tome & Principe");
    public static Country SU = new Country("SU","Union of Soviet Socialist Republics (no longer exists)");
    public static Country SV = new Country("SV","El Salvador");
    public static Country SY = new Country("SY","Syrian Arab Republic");
    public static Country SZ = new Country("SZ","Swaziland");
    public static Country TC = new Country("TC","Turks & Caicos Islands");
    public static Country TD = new Country("TD","Chad");
    public static Country TF = new Country("TF","French Southern Territories");
    public static Country TG = new Country("TG","Togo");
    public static Country TH = new Country("TH","Thailand");
    public static Country TJ = new Country("TJ","Tajikistan");
    public static Country TK = new Country("TK","Tokelau");
    public static Country TM = new Country("TM","Turkmenistan");
    public static Country TN = new Country("TN","Tunisia");
    public static Country TO = new Country("TO","Tonga");
    public static Country TP = new Country("TP","East Timor");
    public static Country TR = new Country("TR","Turkey");
    public static Country TT = new Country("TT","Trinidad & Tobago");
    public static Country TV = new Country("TV","Tuvalu");
    public static Country TW = new Country("TW","Taiwan, Province of China");
    public static Country TZ = new Country("TZ","Tanzania, United Republic of");
    public static Country UA = new Country("UA","Ukraine");
    public static Country UG = new Country("UG","Uganda");
    public static Country UK = new Country("UK","United Kingdom");
    public static Country UM = new Country("UM","United States Minor Outlying Islands");
    public static Country US = new Country("US","United States of America");
    public static Country UY = new Country("UY","Uruguay");
    public static Country UZ = new Country("UZ","Uzbekistan");
    public static Country VA = new Country("VA","Vatican City State (Holy See)");
    public static Country VC = new Country("VC","St. Vincent & the Grenadines");
    public static Country VE = new Country("VE","Venezuela");
    public static Country VG = new Country("VG","British Virgin Islands");
    public static Country VI = new Country("VI","United States Virgin Islands");
    public static Country VN = new Country("VN","Viet Nam");
    public static Country VU = new Country("VU","Vanuatu");
    public static Country WF = new Country("WF","Wallis & Futuna Islands");
    public static Country WS = new Country("WS","Samoa");
    public static Country YD = new Country("YD","Democratic Yemen (no longer exists)");
    public static Country YE = new Country("YE","Yemen");
    public static Country YT = new Country("YT","Mayotte");
    public static Country YU = new Country("YU","Yugoslavia");
    public static Country ZA = new Country("ZA","South Africa");
    public static Country ZM = new Country("ZM","Zambia");
    public static Country ZR = new Country("ZR","Zaire");
    public static Country ZW = new Country("ZW","Zimbabwe");
    public static Country ZZ = new Country("ZZ","Unknown or unspecified country");
    
    // private member variables
    private String countryCode;
    private String name;
    
    
    
    /**
     * The default constructor.
     */
    public Country() {
    }

    
    
    /**
     * The constructor that sets the country code.
     * 
     * @param countryCode The country code.
     */
    public Country(String countryCode, String name) {
        this.countryCode = countryCode;
        this.name = name;
    }

    
    /**
     * This method returns the country code.
     * 
     * @return The string containing the country code.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#CountryCode")
    @Identifier()
    public String getCountryCode() {
        return countryCode;
    }

    
    /**
     * This method sets the country code
     * 
     * @param countryCode The string containing the new country code.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#CountryCode")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    
    /**
     * This method returns the unique identifier for this object.
     * 
     * @return The string containing the unique identifier for this object.
     */
    @Override
    public String getObjId() {
        return countryCode;
    }
    
    
    /**
     * This is the name of the country.
     * 
     * @return The name of the country.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#CountryName")
    public String getName() {
        return name;
    }
    
    
    /**
     * This method sets the name of the country.
     * 
     * @param name The name of the country.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#CountryName")
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * The over riden equals operator.
     * 
     * @param obj The object to perform the comparison on.
     * @return TRUE if equals, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        // make the parent call
        if (!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Country other = (Country) obj;
        if (this.countryCode != other.countryCode && (this.countryCode == null || !this.countryCode.equals(other.countryCode))) {
            return false;
        }
        return true;
    }
    
    
    /**
     * The over ridden hash code operator.
     * 
     * @return The has value of this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.countryCode != null ? this.countryCode.hashCode() : 0);
        return hash;
    }

    
    /**
     * This method returns the country code value.
     * 
     * @return The string value.
     */
    @Override
    public String toString() {
        return this.countryCode;
    }
    
    
    
    
}
