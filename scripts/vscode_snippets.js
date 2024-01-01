// convert vscode snippet  to ultisnippet
Object.values(jsonObj).map(e => {
return `snippet ${e.prefix} \"${e.description}\"
${Array.isArray(e.body) ? e.body.join('\n') : e.body }
endsnippet
`
}).join('\n')

