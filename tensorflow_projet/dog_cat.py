# https://www.tensorflow.org/tutorials/keras/classification

# https://www.tensorflow.org/tutorials/load_data/images

# 
# https://www.tensorflow.org/tutorials/images/classification
# 

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import os
import sys
import cv2
import glob
import pickle
from random import shuffle 
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt
import tensorflow.compat.v2 as tf

from tensorflow.keras.datasets import cifar10
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout, Activation, Flatten
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Softmax

import tensorflow_datasets as tfds
tfds.disable_progress_bar()
tf.enable_v2_behavior()

DATA_DIR = 'PetImages'
DATA_VALIDATE_DIR = 'PetImages/validation'

CATEGORIES = ["Cat","Dog"]

IMG_SIZE = 50

BATCH_SIZE = 32
EPOCHS = 3

training_data = []
validate_data = []

X = []
Y = []

X_VAL = []
Y_VAL = []

def create_training_data(DATA_DIR, CATEGORIES, IMG_SIZE, data_list):

    for category in CATEGORIES:

        path = os.path.join(DATA_DIR,category)
        class_num = CATEGORIES.index(category)

        for img in os.listdir(path):

            try:
                img_array = cv2.imread(os.path.join(path,img), cv2.IMREAD_GRAYSCALE)
                new_array = cv2.resize(img_array, (IMG_SIZE, IMG_SIZE))
                data_list.append([new_array, class_num])
            except Exception as e:
                pass

def load_dataset(DATA_DIR, CATEGORIES, IMG_SIZE, data_list, X, Y, X_NAME, Y_NAME):

	create_training_data(DATA_DIR, CATEGORIES, IMG_SIZE, data_list)

	print(len(data_list))

	shuffle(data_list)

	for features,label in data_list:
	    X.append(features)
	    Y.append(label)

	X = np.array(X).reshape(-1, IMG_SIZE, IMG_SIZE, 1)

	output1 = X_NAME + ".pickle"
	pickle_out = open(output1,"wb")
	pickle.dump(X, pickle_out)
	pickle_out.close()

	output2 = Y_NAME + ".pickle"
	pickle_out = open(Y_NAME,"wb")
	pickle.dump(Y, pickle_out)
	pickle_out.close()

def load_previous_dataset(X, Y, X_NAME, Y_NAME):

	xFile = X_NAME + ".pickle"
	pickle_in = open(xFile,"rb")
	X = pickle.load(pickle_in)
	X = X/255.0

	yFile = Y_NAME + ".pickle"
	pickle_in = open(yFile,"rb")
	Y = pickle.load(pickle_in)

load_dataset(DATA_DIR, CATEGORIES, IMG_SIZE, training_data, X, Y, "X", "Y")
load_dataset(DATA_VALIDATE_DIR, CATEGORIES, IMG_SIZE, validate_data, X_VAL, Y_VAL, "X_VAL", "Y_VAL")

# load_previous_dataset(X,Y,"X","Y")
# load_previous_dataset(X_VAL,Y_VAL,"X_VAL","Y_VAL")

X = np.asarray(X)
Y = np.asarray(Y)
X_VAL = np.asarray(X_VAL)
Y_VAL = np.asarray(Y_VAL)

model = Sequential()

model.add(Conv2D(256, (3, 3), input_shape=(1,IMG_SIZE,IMG_SIZE,1)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(256, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Flatten())  # this converts our 3D feature maps to 1D feature vectors

model.add(Dense(64))

model.add(Dense(1))
model.add(Activation('sigmoid'))

model.compile(loss='binary_crossentropy',
              optimizer='adam',
              metrics=['accuracy'])

model.fit(X, Y, batch_size=BATCH_SIZE, epochs=EPOCHS, validation_split=0.3)

predictions = model.predict(X_VAL)

for i in range(len(predictions)):
    
    predictionItem = CATEGORIES[np.argmax(predictions[i])]
    real = CATEGORIES[Y_VAL[i]]

    plt.grid(False)
    plt.imshow(X_VAL[i], cmap=plt.cm.binary)
    plt.title("prediction : " + predictionItem + " | " + "Real : " + real)
    plt.show()