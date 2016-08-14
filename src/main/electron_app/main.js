"use strict";

const {app, BrowserWindow} = require('electron');


let window = null;

function createWindow() {
  window = new BrowserWindow({width: 800, height: 600});
  window.loadURL("http://localhost:8080/ng1/index.html");
}

app.on("ready", createWindow);

app.on('window-all-closed', ()=> {
  if(process.platform !== 'darwin') {
    app.quit();
  }
});

app.on('activate', ()=> {
  if(win === null) createWindow();
});