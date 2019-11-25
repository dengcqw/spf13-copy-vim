(function() {
const downloadFile = fileName => content => {
    var aTag = document.createElement('a')
    var blob = new Blob([content])
    aTag.download = fileName
    aTag.href = URL.createObjectURL(blob)
    aTag.click()
    URL.revokeObjectURL(blob)
}

$('#fileList a.name').each(function( index ) {
  var href = $(this).get(0).href
  downloadFile(href)("")
});


})()
