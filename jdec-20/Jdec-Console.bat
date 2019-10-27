@echo on



set CP=.;.\jdec20.jar

rem %CP%
 
java -classpath %CP% net.sf.jdec.main.ConsoleLauncher

goto end



:end
set CP=
