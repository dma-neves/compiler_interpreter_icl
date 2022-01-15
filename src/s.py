import os

files = os.listdir('tests')
for file in files:
    print("./run-tests.sh -s " + file[0:-4])