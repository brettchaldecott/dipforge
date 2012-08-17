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
 * @author admin
 */

package pckg.setup

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;


def log = Logger.getLogger("com.dipforge.log.pckg.setup.Setup");

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

// desktop product
def desktopApplicationProduct = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product")
desktopApplicationProduct.setId('desktopApplication')
desktopApplicationProduct.setName('Desktop App Product')
desktopApplicationProduct.setDescription('Desktop App Base Product')
desktopApplicationProduct.setDataType('http://dipforge.sourceforge.net/schema/rdf/1.0/oss/DesktopApplication#DesktopApplication')
def desktopApplicationProductJsConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
desktopApplicationProductJsConfig.setId("Javascript:desktopApplication")
desktopApplicationProductJsConfig.setName("Javascript")
desktopApplicationProductJsConfig.setUrl("js/products/desktopApplication/Config.js")
def desktopApplicationConfiguration = []
desktopApplicationConfiguration.add(desktopApplicationProductJsConfig)
def desktopApplicationProductGroovySetup = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
desktopApplicationProductGroovySetup.setId("Groovy:desktopApplication")
desktopApplicationProductGroovySetup.setName("Groovy")
desktopApplicationProductGroovySetup.setUrl("products.desktopApplication.Config")
desktopApplicationConfiguration.add(desktopApplicationProductGroovySetup)
desktopApplicationProduct.setConfigurationManager(desktopApplicationConfiguration)
desktopApplicationProduct.setCategory(base)
desktopApplicationProduct.setVendor(dipforge)
desktopApplicationProduct.setThumbnail('image/product/desktopApplication.png')
desktopApplicationProduct.setIcon('image/product/desktopApplication-icon.png')
log.info("##### Init the request : " + desktopApplicationProduct.toXML())
RequestHandler.getInstance("bss", "CreateProduct", desktopApplicationProduct).makeRequest()


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


// setup the basic catalog
def catalogEntry = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/CatalogEntry#CatalogEntry")
    
log.info("Setup the values")
catalogEntry.setId("base")
catalogEntry.setName("Base")
catalogEntry.setDescription("Base Catalog")
catalogEntry.setThumbnail("image/catalog/base.png")
catalogEntry.setIcon("image/catalog/base-icon.png")

log.info("##### Init the request : " + catalogEntry.toXML())
RequestHandler.getInstance("bss", "CreateCatalogEntry", catalogEntry).makeRequest()

def catalog = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Catalog#Catalog")
catalog.setId('home')
catalog.setName('Home')
catalog.setDescription('Home Catalog')
catalog.setEntries([catalogEntry]);

log.info("##### Init the request : " + catalog.toXML())
RequestHandler.getInstance("bss", "CreateCatalog", catalog).makeRequest()

