const fs = require('node:fs');
const readline = require('node:readline');
const path = require('path')

fs.readdirSync('./').forEach(file => {
  console.log('----> msg', file)
  try {
    let newData = ""
    const data = fs.readFileSync(file, 'UTF-8')
    const lines = data.split(/\r?\n/)
    lines.forEach(line => {
      if (line.startsWith("#import <")) {
        var newl = line.replace("<", '"')
        newl = newl.replace(">", '"')
        console.log(newl)
        newData = newData + newl + "\n"
      } else {
        newData = newData + line + "\n"
      }
    })
    fs.writeFileSync(file, newData, {flag:'w'})
  } catch (err) {
    console.error(err)
  }
})
