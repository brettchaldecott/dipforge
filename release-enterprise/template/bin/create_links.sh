#!/usr/bin/env bash

# setup tools path
DIPFORGE_HOME=@dipforge_home@
export DIPFORGE_HOME
VERSION=@dipforge_version@
export VERSION

# create link for angular
ln -s ${DIPFORGE_HOME}/var/template/project_angular/node_project/app/ ${DIPFORGE_HOME}/var/template/project_angular/views/app
# create react project link
ln -s ${DIPFORGE_HOME}/var/template/project_react/node_project/src/ ${DIPFORGE_HOME}/var/template/project_react/views/app
# create links for ide2
ln -s ${DIPFORGE_HOME}/var/projects/ide2/node_project/app ${DIPFORGE_HOME}/var/projects/ide2/views/app
# create links for swagger
ln -s ${DIPFORGE_HOME}/var/projects/swagger/node-project/app ${DIPFORGE_HOME}/var/projects/swagger/views/app
