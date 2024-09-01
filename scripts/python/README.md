


```shell
mkdir runtime
cd runtime
pip install optimum

python3 -m venv venv
source ./venv/bin/activate
(venv) pip install --upgrade pip

distilbert/distilbert-base-uncased

(venv) pip install optimum onnx onnxruntime sentence-transformers
(venv) optimum-cli export onnx
```
