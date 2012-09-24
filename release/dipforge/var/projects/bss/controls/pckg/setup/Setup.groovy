/*
 * bss: Description
 * Copyright (C) Wed Jul 25 14:23:53 SAST 2012 owner 
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
 * Setup.groovy
 * @author brett chaldecott
 */

package pckg.setup

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;


def log = Logger.getLogger("com.dipforge.log.pckg.setup.Setup");

try {
    
    def organisation = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Organisation#Organisation")
    organisation.setId("base")
    organisation.setName("Base")
    organisation.setDescription("Base Organisastion")
    organisation.setCode("base")
    organisation.setRole("base")
    organisation.setCreated(new java.util.Date())
    organisation.setModified(new java.util.Date())
    RequestHandler.getInstance("bss", "CreateOrganisation", organisation).makeRequest()
    
    // setup the base category
    def base = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Category#Category")
    base.setId('base')
    base.setName('Base')
    base.setDescription('Base related products')
    base.setThumbnail('image/category/base.png')
    base.setIcon('image/category/base-icon.png')
    log.info("##### Init the request : " + base.toXML())
    RequestHandler.getInstance("bss", "CreateCategory", base).makeRequest()
    
    // setup the domain category
    def domain = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Category#Category")
    domain.setId('domain')
    domain.setName('Domain')
    domain.setDescription('Domain related products')
    domain.setThumbnail('image/category/domain.png')
    domain.setIcon('image/category/domain-icon.png')
    log.info("##### Init the request : " + domain.toXML())
    RequestHandler.getInstance("bss", "CreateCategory", domain).makeRequest()
    
    // setup the web category
    def web = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Category#Category")
    web.setId('web')
    web.setName('Web')
    web.setDescription('Web related products')
    web.setThumbnail('image/category/web.png')
    web.setIcon('image/category/web-icon.png')
    log.info("##### Init the request : " + web.toXML())
    RequestHandler.getInstance("bss", "CreateCategory", web).makeRequest()
    
    // setup the mail category
    def mail = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Category#Category")
    mail.setId('mail')
    mail.setName('Mail')
    mail.setDescription('Mail related products')
    mail.setThumbnail('image/category/mail.png')
    mail.setIcon('image/category/mail-icon.png')
    log.info("##### Init the request : " + mail.toXML())
    RequestHandler.getInstance("bss", "CreateCategory", mail).makeRequest()
    
    // setup the rift it vendor
    def dipforge = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Vendor#Vendor")
    dipforge.setId('Dipforge')
    dipforge.setName('Dipforge')
    dipforge.setDescription('Dipforge')
    dipforge.setThumbnail('image/vendor/dipforge.png')
    dipforge.setIcon('image/vendor/dipforge-icon.png')
    log.info("##### Init the request : " + dipforge.toXML())
    RequestHandler.getInstance("bss", "CreateVendor", dipforge).makeRequest()
    
    // setup the burnt jam vendor
    //def burntJam = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Vendor#Vendor")
    //burntJam.setId('burntjam')
    //burntJam.setName('Burnt Jam')
    //burntJam.setDescription('Burnt Jam')
    //burntJam.setThumbnail('image/vendor/burnt-jam.png')
    //burntJam.setIcon('image/vendor/burnt-jam-icon.png')
    //log.info("##### Init the request : " + burntJam.toXML())
    //RequestHandler.getInstance("bss", "CreateVendor", burntJam).makeRequest()
    
    // user product
    def userProduct = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product")
    userProduct.setId('user')
    userProduct.setName('User Product')
    userProduct.setDescription('User Base Product')
    userProduct.setDataType('http://dipforge.sourceforge.net/schema/rdf/1.0/oss/User#User')
    def userProductJsConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
    userProductJsConfig.setId("Javascript:user")
    userProductJsConfig.setName("Javascript")
    userProductJsConfig.setUrl("js/products/user/Config.js")
    def userConfiguration = []
    userConfiguration.add(userProductJsConfig)
    def userProductGroovySetup = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
    userProductGroovySetup.setId("Groovy:user")
    userProductGroovySetup.setName("Groovy")
    userProductGroovySetup.setUrl("products.user.Config")
    userConfiguration.add(userProductGroovySetup)
    userProduct.setConfigurationManager(userConfiguration)
    userProduct.setCategory(base)
    userProduct.setVendor(dipforge)
    userProduct.setThumbnail('image/product/user.png')
    userProduct.setIcon('image/product/user-icon.png')
    log.info("##### Init the request : " + userProduct.toXML())
    RequestHandler.getInstance("bss", "CreateProduct", userProduct).makeRequest()
    
    // desktop product
    def desktopProduct = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product")
    desktopProduct.setId('desktop')
    desktopProduct.setName('Desktop Product')
    desktopProduct.setDescription('Desktop Base Product')
    desktopProduct.setDataType('http://dipforge.sourceforge.net/schema/rdf/1.0/oss/Desktop#Desktop')
    def desktopProductJsConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
    desktopProductJsConfig.setId("Javascript:desktop")
    desktopProductJsConfig.setName("Javascript")
    desktopProductJsConfig.setUrl("js/products/desktop/Config.js")
    def desktopConfiguration = []
    desktopConfiguration.add(desktopProductJsConfig)
    def desktopProductGroovySetup = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
    desktopProductGroovySetup.setId("Groovy:desktop")
    desktopProductGroovySetup.setName("Groovy")
    desktopProductGroovySetup.setUrl("products.desktop.Config")
    desktopConfiguration.add(desktopProductGroovySetup)
    desktopProduct.setConfigurationManager(desktopConfiguration)
    desktopProduct.setCategory(base)
    desktopProduct.setVendor(dipforge)
    desktopProduct.setThumbnail('image/product/desktop.png')
    desktopProduct.setIcon('image/product/desktop-icon.png')
    log.info("##### Init the request : " + desktopProduct.toXML())
    RequestHandler.getInstance("bss", "CreateProduct", desktopProduct).makeRequest()
    
    // application product
    def applicationProduct = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product")
    applicationProduct.setId('application')
    applicationProduct.setName('Desktop Application')
    applicationProduct.setDescription('Desktop App Base Product')
    applicationProduct.setDataType('http://dipforge.sourceforge.net/schema/rdf/1.0/oss/DesktopApplication#DesktopApplication')
    def applicationProductJsConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
    applicationProductJsConfig.setId("Javascript:application")
    applicationProductJsConfig.setName("Javascript")
    applicationProductJsConfig.setUrl("js/products/application/Config.js")
    def applicationConfiguration = []
    applicationConfiguration.add(applicationProductJsConfig)
    def applicationProductGroovySetup = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
    applicationProductGroovySetup.setId("Groovy:application")
    applicationProductGroovySetup.setName("Groovy")
    applicationProductGroovySetup.setUrl("products.application.Config")
    applicationConfiguration.add(applicationProductGroovySetup)
    applicationProduct.setConfigurationManager(applicationConfiguration)
    applicationProduct.setCategory(base)
    applicationProduct.setVendor(dipforge)
    applicationProduct.setThumbnail('image/product/desktopApplication.png')
    applicationProduct.setIcon('image/product/desktopApplication-icon.png')
    log.info("##### Init the request : " + applicationProduct.toXML())
    RequestHandler.getInstance("bss", "CreateProduct", applicationProduct).makeRequest()
    
    
    // domain product
    def domainProduct = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product")
    domainProduct.setId('domain')
    domainProduct.setName('Domain Product')
    domainProduct.setDescription('Domain Base Product')
    domainProduct.setDataType('http://dipforge.sourceforge.net/schema/rdf/1.0/oss/Domain#Domain')
    def domainProductJsConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
    domainProductJsConfig.setId("Javascript:domain")
    domainProductJsConfig.setName("Javascript")
    domainProductJsConfig.setUrl("js/products/domain/Config.js")
    def domainConfiguration = []
    domainConfiguration.add(domainProductJsConfig)
    def domainProductGroovySetup = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
    domainProductGroovySetup.setId("Groovy:domain")
    domainProductGroovySetup.setName("Groovy")
    domainProductGroovySetup.setUrl("products.domain.Config")
    domainConfiguration.add(domainProductGroovySetup)
    domainProduct.setConfigurationManager(domainConfiguration)
    domainProduct.setCategory(domain)
    domainProduct.setVendor(dipforge)
    domainProduct.setThumbnail('image/product/domain.png')
    domainProduct.setIcon('image/product/domain-icon.png')
    log.info("##### Init the request : " + domainProduct.toXML())
    RequestHandler.getInstance("bss", "CreateProduct", domainProduct).makeRequest()
        
    // web product
    def webProduct = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product")
    webProduct.setId('web')
    webProduct.setName('Web Product')
    webProduct.setDescription('Web Base Product')
    webProduct.setDataType('http://dipforge.sourceforge.net/schema/rdf/1.0/oss/Web#Web')
    def webProductJsConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
    webProductJsConfig.setId("Javascript:web")
    webProductJsConfig.setName("Javascript")
    webProductJsConfig.setUrl("js/products/web/Config.js")
    def webConfiguration = []
    webConfiguration.add(webProductJsConfig)
    def webProductGroovySetup = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
    webProductGroovySetup.setId("Groovy:web")
    webProductGroovySetup.setName("Groovy")
    webProductGroovySetup.setUrl("products.web.Config")
    webConfiguration.add(webProductGroovySetup)
    webProduct.setConfigurationManager(webConfiguration)
    webProduct.setCategory(web)
    webProduct.setVendor(dipforge)
    webProduct.setThumbnail('image/product/web.png')
    webProduct.setIcon('image/product/web-icon.png')
    webProduct.setDependency(domainProduct)
    log.info("##### Init the request : " + webProduct.toXML())
    RequestHandler.getInstance("bss", "CreateProduct", webProduct).makeRequest()
    
    // domain product
    def mailProduct = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product")
    mailProduct.setId('mail')
    mailProduct.setName('Mail Product')
    mailProduct.setDescription('Mail Base Product')
    mailProduct.setDataType('http://dipforge.sourceforge.net/schema/rdf/1.0/oss/Mail#Mail')
    def mailProductJsConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
    mailProductJsConfig.setId("Javascript:mail")
    mailProductJsConfig.setName("Javascript")
    mailProductJsConfig.setUrl("js/products/mail/Config.js")
    def mailConfiguration = []
    mailConfiguration.add(mailProductJsConfig)
    def mailProductGroovySetup = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
    mailProductGroovySetup.setId("Groovy:mail")
    mailProductGroovySetup.setName("Groovy")
    mailProductGroovySetup.setUrl("products.mail.Config")
    mailConfiguration.add(mailProductGroovySetup)
    mailProduct.setConfigurationManager(mailConfiguration)
    mailProduct.setCategory(mail)
    mailProduct.setVendor(dipforge)
    mailProduct.setThumbnail('image/product/mail.png')
    mailProduct.setIcon('image/product/mail-icon.png')
    mailProduct.setDependency(domainProduct)
    log.info("##### Init the request : " + mailProduct.toXML())
    RequestHandler.getInstance("bss", "CreateProduct", mailProduct).makeRequest()
    
    
    // setup a package
    def guestUserPckg = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#Pckg")
        
    log.debug("Set the values")
    guestUserPckg.setId('base-guest-pckg')
    guestUserPckg.setName('Base Guest Packge')
    guestUserPckg.setDescription('Base Guest Packge')
    guestUserPckg.setThumbnail('image/pckg/user.png')
    guestUserPckg.setIcon('image/pckg/user-icon.png')
    def guestUserPckgConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfig#ProductConfig")
    def guestUserPckgProducts = []
    guestUserPckgConfig.setId("user:base-guest-package")
    guestUserPckgConfig.setProduct(userProduct)
    guestUserPckgConfig.setData("guest")
    guestUserPckgProducts.add(guestUserPckgConfig)
    def guestUserDesktopPckgConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfig#ProductConfig")
    guestUserDesktopPckgConfig.setId("desktop:base-guest-package")
    guestUserDesktopPckgConfig.setProduct(desktopProduct)
    guestUserDesktopPckgConfig.setData("Dipforge")
    guestUserPckgProducts.add(guestUserDesktopPckgConfig)
    def guestUserApplicationPckgConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfig#ProductConfig")
    guestUserApplicationPckgConfig.setId("application:base-guest-package")
    guestUserApplicationPckgConfig.setProduct(applicationProduct)
    guestUserApplicationPckgConfig.setData("name=IDE,principle=ide,url=/desktop/ide/,thumbnail=images/icon/IDE.png,icon=images/icon/IDE.png:name=Documentation,principle=none,url=/desktop/documentation/,thumbnail=images/icon/Documents.png,icon=images/icon/Documents.png:name=File Manager,principle=none,url=/desktop/FileManager/path/,thumbnail=images/icon/FileManager.png,icon=images/icon/FileManager.png")
    guestUserPckgProducts.add(guestUserApplicationPckgConfig)
    
    guestUserPckg.setProducts(guestUserPckgProducts)
    
    log.debug("##### Init the request : " + guestUserPckg.toXML())
    RequestHandler.getInstance("bss", "CreatePckg", guestUserPckg).makeRequest()
    
    def userPckg = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#Pckg")
    
    log.debug("Set the values")
    userPckg.setId('base-user-pckg')
    userPckg.setName('Base User Packge')
    userPckg.setDescription('Base User Packge')
    userPckg.setThumbnail('image/pckg/user.png')
    userPckg.setIcon('image/pckg/user-icon.png')
    def userPckgConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfig#ProductConfig")
    def userPckgProducts = []
    userPckgConfig.setId("user:base-user-package")
    userPckgConfig.setProduct(userProduct)
    userPckgConfig.setData("user")
    userPckgProducts.add(userPckgConfig)
    def userDesktopPckgConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfig#ProductConfig")
    userDesktopPckgConfig.setId("desktop:base-user-package")
    userDesktopPckgConfig.setProduct(desktopProduct)
    userDesktopPckgConfig.setData("Dipforge")
    userPckgProducts.add(userDesktopPckgConfig)
    def userApplicationPckgConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfig#ProductConfig")
    userApplicationPckgConfig.setId("application:base-user-package")
    userApplicationPckgConfig.setProduct(applicationProduct)
    userApplicationPckgConfig.setData("name=IDE,principle=ide,url=/desktop/ide/,thumbnail=images/icon/IDE.png,icon=images/icon/IDE.png:name=Documentation,principle=none,url=/desktop/documentation/,thumbnail=images/icon/Documents.png,icon=images/icon/Documents.png:name=File Manager,principle=none,url=/desktop/FileManager/path/,thumbnail=images/icon/FileManager.png,icon=images/icon/FileManager.png:name=Administration,principle=none,url=/desktop/bss/,thumbnail=images/icon/Organisation.png,icon=images/icon/Organisation.png")
    userPckgProducts.add(userApplicationPckgConfig)
    
    userPckg.setProducts(userPckgProducts)
    
    log.debug("##### Init the request : " + userPckg.toXML())
    RequestHandler.getInstance("bss", "CreatePckg", userPckg).makeRequest()
    
    def techUserPckg = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#Pckg")
    
    log.debug("Set the values")
    techUserPckg.setId('base-tech-user-pckg')
    techUserPckg.setName('Base Technical User Packge')
    techUserPckg.setDescription('Base Technical User Packge')
    techUserPckg.setThumbnail('image/pckg/user.png')
    techUserPckg.setIcon('image/pckg/user-icon.png')
    def techUserPckgConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfig#ProductConfig")
    def techUserPckgProducts = []
    techUserPckgConfig.setId("user:base-tech-user-package")
    techUserPckgConfig.setProduct(userProduct)
    techUserPckgConfig.setData("technical")
    techUserPckgProducts.add(techUserPckgConfig)
    def techUserDesktopPckgConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfig#ProductConfig")
    techUserDesktopPckgConfig.setId("desktop:base-tech-user-package")
    techUserDesktopPckgConfig.setProduct(desktopProduct)
    techUserDesktopPckgConfig.setData("Dipforge")
    techUserPckgProducts.add(techUserDesktopPckgConfig)
    def techUserApplicationPckgConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfig#ProductConfig")
    techUserApplicationPckgConfig.setId("application:base-tech-user-package")
    techUserApplicationPckgConfig.setProduct(applicationProduct)
    techUserApplicationPckgConfig.setData("name=IDE,principle=ide,url=/desktop/ide/,thumbnail=images/icon/IDE.png,icon=images/icon/IDE.png:name=Documentation,principle=none,url=/desktop/documentation/,thumbnail=images/icon/Documents.png,icon=images/icon/Documents.png:name=File Manager,principle=none,url=/desktop/FileManager/path/,thumbnail=images/icon/FileManager.png,icon=images/icon/FileManager.png:name=Administration,principle=none,url=/desktop/bss/,thumbnail=images/icon/Organisation.png,icon=images/icon/Organisation.png:name=Audit Trail,principle=admin,url=/desktop/AuditTrailConsole/,thumbnail=images/icon/Audit.png,icon=images/icon/Audit.png")
    techUserPckgProducts.add(techUserApplicationPckgConfig)
    
    techUserPckg.setProducts(techUserPckgProducts)
    
    log.debug("##### Init the request : " + techUserPckg.toXML())
    RequestHandler.getInstance("bss", "CreatePckg", techUserPckg).makeRequest()
    
    def adminUserPckg = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#Pckg")
    
    log.debug("Set the values")
    adminUserPckg.setId('base-admin-user-pckg')
    adminUserPckg.setName('Base Admin User Packge')
    adminUserPckg.setDescription('Base Admin User Packge')
    adminUserPckg.setThumbnail('image/pckg/user.png')
    adminUserPckg.setIcon('image/pckg/user-icon.png')
    def adminUserPckgConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfig#ProductConfig")
    def adminUserPckgProducts = []
    adminUserPckgConfig.setId("user:base-admin-user-package")
    adminUserPckgConfig.setProduct(userProduct)
    adminUserPckgConfig.setData("admin")
    adminUserPckgProducts.add(adminUserPckgConfig)
    def adminUserDesktopPckgConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfig#ProductConfig")
    adminUserDesktopPckgConfig.setId("desktop:base-admin-user-package")
    adminUserDesktopPckgConfig.setProduct(desktopProduct)
    adminUserDesktopPckgConfig.setData("Dipforge")
    adminUserPckgProducts.add(adminUserDesktopPckgConfig)
    def adminUserApplicationPckgConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfig#ProductConfig")
    adminUserApplicationPckgConfig.setId("application:base-admin-user-package")
    adminUserApplicationPckgConfig.setProduct(applicationProduct)
    adminUserApplicationPckgConfig.setData("name=IDE,principle=ide,url=/desktop/ide/,thumbnail=images/icon/IDE.png,icon=images/icon/IDE.png:name=Documentation,principle=none,url=/desktop/documentation/,thumbnail=images/icon/Documents.png,icon=images/icon/Documents.png:name=File Manager,principle=none,url=/desktop/FileManager/path/,thumbnail=images/icon/FileManager.png,icon=images/icon/FileManager.png:name=Administration,principle=none,url=/desktop/bss/,thumbnail=images/icon/Organisation.png,icon=images/icon/Organisation.png:name=Audit Trail,principle=none,url=/desktop/AuditTrailConsole/,thumbnail=images/icon/Audit.png,icon=images/icon/Audit.png:name=Admin,principle=none,url=/desktop/DipforgeAdmin/,thumbnail=images/icon/Admin.png,icon=images/icon/Admin.png:name=Tomcat,principle=none,url=/desktop/manager/html,thumbnail=images/icon/Admin.png,icon=images/icon/Admin.png")
    adminUserPckgProducts.add(adminUserApplicationPckgConfig)
    
    adminUserPckg.setProducts(adminUserPckgProducts)
    
    log.debug("##### Init the request : " + adminUserPckg.toXML())
    RequestHandler.getInstance("bss", "CreatePckg", adminUserPckg).makeRequest()
    
    
    // setup the basic catalog
    def userCatalogEntry = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/CatalogEntry#CatalogEntry")
    
    log.info("Setup the values")
    userCatalogEntry.setId("user-base")
    userCatalogEntry.setName("User Base")
    userCatalogEntry.setDescription("User Base Catalog")
    userCatalogEntry.setThumbnail("image/catalog/base.png")
    userCatalogEntry.setIcon("image/catalog/base-icon.png")
    
    log.info("##### Init the request : " + userCatalogEntry.toXML())
    RequestHandler.getInstance("bss", "CreateCatalogEntry", userCatalogEntry).makeRequest()
    
    def catalogEntry = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/CatalogEntry#CatalogEntry")
    log.info("Setup the values")
    catalogEntry.setId("base")
    catalogEntry.setName("Base")
    catalogEntry.setDescription("Base Catalog")
    catalogEntry.setThumbnail("image/catalog/base.png")
    catalogEntry.setIcon("image/catalog/base-icon.png")
    catalogEntry.setChildren([userCatalogEntry])
    
    log.info("##### Init the request : " + catalogEntry.toXML())
    RequestHandler.getInstance("bss", "CreateCatalogEntry", catalogEntry).makeRequest()
    
    def catalog = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Catalog#Catalog")
    catalog.setId('home')
    catalog.setName('Home')
    catalog.setDescription('Home Catalog')
    catalog.setEntries([catalogEntry]);
    catalog.setOrganisation(organisation);
    
    log.info("##### Init the request : " + catalog.toXML())
    RequestHandler.getInstance("bss", "CreateCatalog", catalog).makeRequest()
    
    def guestUserOffering = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering")
    
    guestUserOffering.setId('base-guest-offering')
    guestUserOffering.setName('Base Guest')
    guestUserOffering.setDescription('Base Guest Offering')
    guestUserOffering.setThumbnail('image/offering/user.png')
    guestUserOffering.setIcon('image/offering/user-icon.png')
    guestUserOffering.setPckg(guestUserPckg)
    guestUserOffering.setCatalog(userCatalogEntry)
    guestUserOffering.setCreated(new java.util.Date());
    guestUserOffering.setStatus("active");
    log.debug("##### Package xml : " + guestUserOffering.getPckg().toXML())
    RequestHandler.getInstance("bss", "CreateOffering", guestUserOffering).makeRequest()
    
    def userOffering = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering")
    
    userOffering.setId('base-user-offering')
    userOffering.setName('Base User')
    userOffering.setDescription('Base User Offering')
    userOffering.setThumbnail('image/offering/user.png')
    userOffering.setIcon('image/offering/user-icon.png')
    userOffering.setPckg(userPckg)
    userOffering.setCatalog(userCatalogEntry)
    userOffering.setCreated(new java.util.Date());
    userOffering.setStatus("active");
    log.debug("##### Package xml : " + userOffering.getPckg().toXML())
    RequestHandler.getInstance("bss", "CreateOffering", userOffering).makeRequest()
    
    def techUserOffering = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering")
    
    techUserOffering.setId('base-tech-user-offering')
    techUserOffering.setName('Base Technical User')
    techUserOffering.setDescription('Base Technical User Offering')
    techUserOffering.setThumbnail('image/offering/user.png')
    techUserOffering.setIcon('image/offering/user-icon.png')
    techUserOffering.setPckg(techUserPckg)
    techUserOffering.setCatalog(userCatalogEntry)
    techUserOffering.setCreated(new java.util.Date());
    techUserOffering.setStatus("active");
    log.debug("##### Package xml : " + techUserOffering.getPckg().toXML())
    RequestHandler.getInstance("bss", "CreateOffering", techUserOffering).makeRequest()
    
    def adminUserOffering = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering")
    
    adminUserOffering.setId('base-admin-user-offering')
    adminUserOffering.setName('Base Admin User')
    adminUserOffering.setDescription('Base Admin User Offering')
    adminUserOffering.setThumbnail('image/offering/user.png')
    adminUserOffering.setIcon('image/offering/user-icon.png')
    adminUserOffering.setPckg(adminUserPckg)
    adminUserOffering.setCatalog(userCatalogEntry)
    adminUserOffering.setCreated(new java.util.Date());
    adminUserOffering.setStatus("active");
    log.debug("##### Package xml : " + adminUserOffering.getPckg().toXML())
    RequestHandler.getInstance("bss", "CreateOffering", adminUserOffering).makeRequest()
    
    print "success"
} catch (Exception ex) {
    log.error("[1] Failed to setup the system correctly : ${ex.getMessage()}",ex)
    print "fail: failed to setup the default portfolio."
}
