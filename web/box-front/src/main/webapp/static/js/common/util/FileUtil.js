/**
 * 文件处理相关公共方法
 * by HECJ
 */

var FileUtil = FileUtil || {};


/*
 * 获取文件后缀名 
 */
FileUtil.getFileExt = function(file_name) {
	var result = /\.[^\.]+/.exec(file_name);
	return result;
}

/*
 * 判断文件是否是图片
 * .jpg,.jpeg,.png,.gif
 */
FileUtil.isPicture = function(file_name){
	var exts = ".jpg,.jpeg,.png,.gif";
	var ext = this.getFileExt(file_name);
	if(exts.indexOf(ext[0].toLowerCase()) != -1){
		return true;
	}else{
		return false;
	}
}
