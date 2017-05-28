/*
 * test: Description
 * Copyright (C) Mon Apr 10 10:58:24 UTC 2017 owner 
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
 * Swagger.groovy
 * @author admin
 */

package com.dipforge.project


response.setContentType("application/json");

println """
{
   "swagger":"2.0",
   "info":{
      "description":"This is the API for the IDE2 for Dipforge.",
      "version":"1.0.0",
      "title":"IDE2 Dipforge",
      "termsOfService":"",
      "contact":{
         "email":"info@burntjam.com"
      },
      "license":{
         "name":"Comercial",
         "url":""
      }
   },
   "host":"${request.getServerName()}:${request.getServerPort()}",
   "basePath":"/DipforgeWeb/ide2",
   "tags":[
      {
         "name":"user",
         "description":"Operations about user",
         "externalDocs":{
            "description":"Find out more about our store",
            "url":"http://swagger.io"
         }
      }
   ],
   "schemes":[
      "http"
   ],
   "paths":{
      "/com/dipforge/ide/ListProjects.groovy":{
         "get":{
            "tags":[
               "projects"
            ],
            "summary":"List Projects",
            "description":"Retrieve the list of Projects.",
            "operationId":"ListProjects",
            "produces":[
               "application/json"
            ],
            "parameters":[
            ],
            "responses":{
               "200":{
                  "description":"successful operation",
                  "schema":{
                     "type":"array",
                     "items":{
                        "\$ref":"#/definitions/Project"
                     }
                  }
               },
               "400":{
                  "description":"Invalid tag value"
               }
            }
         },
      },
      "/com/dipforge/ide/ListFiles.groovy?project={project}&path={path}":{
         "get":{
            "tags":[
               "files"
            ],
            "summary":"List Files",
            "description":"Retrieve the list of files.",
            "operationId":"ListFiles",
            "produces":[
               "application/json"
            ],
            "parameters":[
                {
                    "name":"project",
                    "in":"query",
                    "description":"The name of the project",
                    "required":true,
                    "type":"string",
                    "format":"int64"
                },
                {
                    "name":"path",
                    "in":"query",
                    "description":"The path within project",
                    "required":false,
                    "type":"string",
                    "format":"int64"
                }
            ],
            "responses":{
               "200":{
                  "description":"successful operation",
                  "schema":{
                     "type":"array",
                     "items":{
                        "\$ref":"#/definitions/Files"
                     }
                  }
               },
               "400":{
                  "description":"Invalid tag value"
               }
            }
         }
      },
      "/com/dipforge/ide/GetFile.groovy?project={project}&path={path}":{
         "get":{
            "tags":[
               "files"
            ],
            "summary":"Get File",
            "description":"Retrieve the file for a project and path.",
            "operationId":"GetFile",
            "produces":[
               "application/json"
            ],
            "parameters":[
                {
                    "name":"project",
                    "in":"query",
                    "description":"The name of the project",
                    "required":true,
                    "type":"string",
                    "format":"int64"
                },
                {
                    "name":"path",
                    "in":"query",
                    "description":"The path within project",
                    "required":true,
                    "type":"string",
                    "format":"int64"
                }
            ],
            "responses":{
               "200":{
                  "description":"successful operation",
                  "schema":{
                     "type":"array",
                     "items":{
                        "\$ref":"#/definitions/File"
                     }
                  }
               },
               "400":{
                  "description":"Invalid tag value"
               }
            }
         }
      },
      "/com/dipforge/ide/SaveFile.groovy":{
         "post":{
            "tags":[
               "files"
            ],
            "summary":"Save a file",
            "description":"Save a file",
            "operationId":"saveFile",
            "consumes":[
               "application/json"
            ],
            "produces":[
               "application/json"
            ],
            "parameters":[
               {
                  "in":"body",
                  "name":"body",
                  "description":"File that needs to be saved",
                  "required":true,
                  "schema":{
                     "\$ref":"#/definitions/File"
                  }
               }
            ],
            "responses":{
               "200":{
                  "description":"successful operation",
                  "schema":{
                     "type":"array",
                     "items":{
                        "\$ref":"#/definitions/File"
                     }
                  }
               },
               "405":{
                  "description":"Invalid input"
               }
            }
         }
      },
      "/com/dipforge/ide/CreateProject.groovy":{
         "post":{
            "tags":[
               "projects"
            ],
            "summary":"Create a project",
            "description":"Create a project",
            "operationId":"createProject",
            "consumes":[
               "application/json"
            ],
            "produces":[
               "application/json"
            ],
            "parameters":[
               {
                  "in":"body",
                  "name":"body",
                  "description":"Project ",
                  "required":true,
                  "schema":{
                     "\$ref":"#/definitions/Project"
                  }
               }
            ],
            "responses":{
               "200":{
                  "description":"successful operation",
                  "schema":{
                     "type":"array",
                     "items":{
                        "\$ref":"#/definitions/Project"
                     }
                  }
               },
               "405":{
                  "description":"Invalid input"
               }
            }
         }
      },
      "/com/dipforge/ide/DeleteProject.groovy":{
         "post":{
            "tags":[
               "projects"
            ],
            "summary":"Delete a project",
            "description":"Delete a project",
            "operationId":"deleteProject",
            "consumes":[
               "application/json"
            ],
            "produces":[
               "application/json"
            ],
            "parameters":[
               {
                  "in":"body",
                  "name":"body",
                  "description":"Project ",
                  "required":true,
                  "schema":{
                     "\$ref":"#/definitions/Project"
                  }
               }
            ],
            "responses":{
               "200":{
                  "description":"successful operation",
                  "schema":{
                     "type":"array",
                     "items":{
                        "\$ref":"#/definitions/Project"
                     }
                  }
               },
               "405":{
                  "description":"Invalid input"
               }
            }
         }
      }
   },
   "securityDefinitions":{
   },
   "definitions":{
      "Project":{
         "type":"object",
         "properties":{
            "name":{
               "type":"string"
            },
            "description":{
               "type":"string"
            }
         },
         "xml":{
            "name":"Project"
         }
      },
      "Files":{
         "type":"object",
         "properties":{
            "project":{
               "type":"string"
            },
            "path":{
               "type":"string"
            },
            "leafNode":{
               "type":"boolean"
            },
            "iconCls":{
               "type":"string"
            },
            "fileExtension":{
               "type":"string"
            }
         },
         "xml":{
            "name":"Files"
         }
      },
      "File":{
         "type":"object",
         "properties":{
            "project":{
               "type":"string"
            },
            "path":{
               "type":"string"
            },
            "contents":{
               "type":"string"
            },
            "fileExtension":{
               "type":"string"
            }
         },
         "xml":{
            "name":"File"
         }
      }
   },
   "externalDocs":{
      "description":"Find out more about Swagger",
      "url":"http://swagger.io"
   }
}
"""



