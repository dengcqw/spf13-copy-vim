#!/usr/bin/env node

const path = require('path')
const fs = require('fs')
const readline = require('readline')
const child_process = require('child_process')

let fileNames = fs.readdirSync('./').filter(fileName=>fileName.includes("xcodeproj"))

//接受一个参数target，就不需要选择
let target = process.argv[2]

switch (fileNames.length) {
  case 0:
    console.log(" Not found Xcode project ")
    process.exit(1)
    break
  case 1:
    if (target) {
      startServer(target, fileNames[0])
    } else {
      listTarget(fileNames[0])
    }
    break
  default:
    chooseProject(fileNames)
}


function startServer(target, project) {
    let command = `sourcekittendaemon start --target ${target} --port 8080 --project ${project}`
    console.log("starting server: ", command)
    let child = child_process.exec(command, function (err, stdout, stderr) {
      console.log("----> start error: ", err)
      process.exit(0)
      child.exit(0)
    })
    child.stdout.on('data', data => console.log(data))
}


function chooseProject(fileNames) {
  console.log("Found Xcode project:")
  fileNames.forEach((fileName, index) => {
    console.log("    ", index, fileName)
  })

  const rl = readline.createInterface({input : process.stdin, output : process.stdout});
  rl.question(`choose project[0-${fileNames.length-1}]:`, (answer) => {

    console.log(`You select: ${fileNames[answer]}`)
    rl.close();
    if (fileNames[answer]) {
      listTarget(fileNames[answer])
    } else {
      console.log("Invalid Number");
    }
  });
}

function chooseTarget(project, targets) {
  console.log("Found project targets:")
  targets.forEach((target, index) => {
    console.log("    ", index, target)
  })

  const rl = readline.createInterface({input : process.stdin, output : process.stdout});
  rl.question(`choose target[0-${targets.length-1}]:`, (answer) => {

    console.log(`You select: ${targets[answer]}`)
    rl.close()
    if (targets[answer]) {
      startServer(targets[answer], project)
    } else {
      console.log("Invalid Number");
      process.exit(0)
    }
  });
}

function listTarget(projectPath) {
  let absolutePath = path.resolve(process.cwd(), projectPath)
  child_process.exec('xcodebuild -list -json -project '+projectPath, function (err, projectInfo, stderr) {
    try {
      let targets = JSON.parse(projectInfo)["project"]["targets"]
      chooseTarget(absolutePath, targets)
    } catch (err) {
      console.log("list project target error: ", err)
    }
  })
}

