@del /q api\src\net\combatserver\protobuf\*.as
@cd proto
@for %%n in (*.proto) do  @..\protoc %%n --plugin=protoc-gen-as3="..\protoc-gen-as3.bat" --as3_out=..\api\src\ 
@cd..