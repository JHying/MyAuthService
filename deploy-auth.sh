#!/bin/bash

NAME="auth-service"
ROOT_DIR="/home/user/myfile/app"
PORT="8099"

#建立應用儲存資料夾
if [ -d $ROOT_DIR/$NAME ]; then
	echo "directory already exist."
else
	mkdir $ROOT_DIR/$NAME
fi

#移動至應用資料夾
mv $NAME.jar $ROOT_DIR/$NAME
mv Dockerfile $ROOT_DIR/$NAME
cd $ROOT_DIR/$NAME/

#刪除舊鏡像及容器
if [[ -n $(docker ps -a -q -f "name=^$NAME$") ]];then
	docker stop $NAME
	docker rm $NAME
	docker rmi $NAME
else
	echo "container not exist."
fi
#建立鏡像
docker build -t $NAME . --no-cache
echo "docker image build finished."
#執行容器
docker run -d --network dev-net --name $NAME -p ${PORT}:${PORT} -v $ROOT_DIR/logs:/usr/myfile/logs $NAME
echo "docker run finished."