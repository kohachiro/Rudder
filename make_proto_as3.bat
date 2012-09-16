@cd proto
@for %%n in (*.proto) do  @..\protoc %%n --plugin=protoc-gen-as3="..\protoc-gen-as3.bat" --as3_out=..\as3 
@cd..