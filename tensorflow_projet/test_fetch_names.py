import os

ROOT_DIR = "test/train"

print(os.listdir(ROOT_DIR))

for category in os.listdir(ROOT_DIR):

	print(os.path.join(ROOT_DIR,category))

	for image in os.listdir(os.path.join(ROOT_DIR,category)):

		print(image)