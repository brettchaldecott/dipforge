"use strict";


let url = "http://www.google.com";
let page = url.open();
page.findById("lst-ib").click();
page.findById("lst-ib").type("test this");
page.findByName("btnK").click();
//page.findByCss("h3.LC20lb").click();
