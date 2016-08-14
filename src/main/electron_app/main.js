"use strict";

const {app, BrowserWindow} = require('electron');


let window = null;

function createWindow() {
  window = new BrowserWindow({width: 1024, height: 768});
  window.loadURL("http://localhost:8080/ng1/index.html");
}

app.commandLine.appendSwitch('--disable-http-cache');

app.on("ready", createWindow);

app.on('window-all-closed', ()=> {
  if(process.platform !== 'darwin') {
    app.quit();
  }
});

app.on('activate', ()=> {
  if(win === null) createWindow();
});