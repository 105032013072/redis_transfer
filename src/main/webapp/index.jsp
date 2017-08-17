<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<form role="form" name="form_export" id="form_export"  action="${pageContext.request.contextPath}/exportData"  method="post">
        <button  id="serach">
              系统配置导出(redis-6379)
        </button>
        </form>
</body>
<br>
<br>

<form role="form" name="form_import" id="form_import"  action="${pageContext.request.contextPath}/"  method="post">
        <button  id="serach">
              系统配置导入(redis-6378)
        </button>
        </form>
</body>
</html>
