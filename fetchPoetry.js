#!/usr/bin/env node

var http = require('https')
var path = require('path');
var fs = require('fs');
const homedir = require('os').homedir();
//https://gushi.ci/
/*
{
    "content": "闲门向山路，深柳读书堂。",
    "origin": "阙题",
    "author": "刘昚虚",
    "category": "古诗文-人生-读书"
}
*/

http.get('https://v1.jinrishici.com/all.json', (res) => {
  const { statusCode } = res;
  const contentType = res.headers['content-type'];

  let error;
  // 任何 2xx 状态码都表示成功的响应，但是这里只检查 200。
  if (statusCode !== 200) {
    error = new Error('请求失败\n' +
                      `状态码: ${statusCode}`);
  } else if (!/^application\/json/.test(contentType)) {
    error = new Error('无效的 content-type.\n' +
                      `期望的是 application/json 但接收到的是 ${contentType}`);
  }
  if (error) {
    console.error(error.message);
    // 消费响应的数据来释放内存。
    res.resume();
    return;
  }

  res.setEncoding('utf8');
  let rawData = '';
  res.on('data', (chunk) => { rawData += chunk; });
  res.on('end', () => {
    try {
      const parsedData = JSON.parse(rawData);
      let string = `    \\["${parsedData.content}", "", " - ${parsedData.author}(${parsedData.category})"],`
      console.log(parsedData.content + '  -- ' + parsedData.author);

      const filePath = homedir + '/spf13-copy-vim/vimrc.startify.headers'
      const data = fs.readFileSync(filePath, 'utf8').split('\n')
      data.splice(data.length - 2, 0, string)
      fs.writeFileSync(filePath, data.join('\n'), 'utf8')
    } catch (e) {
      console.error(e.message);
    }
  });
}).on('error', (e) => {
  console.error(`出现错误: ${e.message}`);
});
