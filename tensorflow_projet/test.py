# https://www.tensorflow.org/tutorials/keras/classification

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import os
import sys
import cv2
import glob
from parser import load_data
from random import shuffle 
import tensorflow as tf
from tensorflow import keras
import numpy as np
import matplotlib.pyplot as plt
import tensorflow.compat.v2 as tf
from tensorflow.keras.preprocessing import ImageDataGenerator

import tensorflow_datasets as tfds
tfds.disable_progress_bar()
tf.enable_v2_behavior()

train_dir = load_data('PetImages/train')
validation_dir = load_data('PetImages/validation')

train_cats = os.path.join(train_dir, 'Cat')
train_dogs = os.path.join(train_dir, 'Dog')
validation_cats = os.path.join(validation_dir, 'Cat')
validation_dogs = os.path.join(validation_dir, 'Dog')

nbr_cats_train = len(os.listdir(train_cats))
nbr_dogs_train = len(os.listdir(train_dogs))
nbr_cats_validation = len(os.listdir(validation_cats))
nbr_dogs_validation = len(os.listdir(validation_dogs))

total_train = nbr_cats_train + nbr_dogs_train
total_validation = nbr_cats_validation + nbr_dogs_validation

BATCH_SIZE = 32
IMG_SIZE = 150

def load_image(addr):

	img = cv2.imread(addr)

	if img is None:
		return None

	img = cv2.resize(img, (IMG_WIDTH, IMG_HEIGHT), interpolation=cv2.INTER_CUBIC)
	img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

	return img

data = keras.datasets.fashion_mnist

# features = tfds.features.FeaturesDict({
#     'bbox': BBoxFeature(shape=(4,), dtype=tf.float32),
#     'image': Image(shape=(None, None, 3), dtype=tf.uint8),
#     'label': ClassLabel(shape=(), dtype=tf.int64, num_classes=196),
# })

(train_images, train_labels), (test_images,  test_labels) = data.load_data()

class_names = ['T-shirt/top', 'Trouser', 'Pullover', 'Dress', 'Coat',
               'Sandal', 'Shirt', 'Sneaker', 'Bag', 'Ankle boot']

train_images = train_images / 255.0
test_images = test_images / 255.0

# model = keras.Sequential([
#     keras.layers.Flatten(input_shape=(28, 28)),
#     keras.layers.Dense(128, activation='relu'),
#     keras.layers.Dense(10, activation='softmax')
# ])
model = keras.Sequential([
    keras.layers.Flatten(input_shape=(28, 28)),
    keras.layers.Dense(128, activation='relu'),
    keras.layers.Dense(10)
])

probability_model = tf.keras.Sequential([model, 
                                         tf.keras.layers.Softmax()])

# model.compile(optimizer='adam',
#               loss="sparse_categorical_crossentropy",
#               metrics=['accuracy'])
model.compile(optimizer='adam',
              loss=tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True),
              metrics=['accuracy'])

model.fit(train_images, train_labels, epochs=5)

# prediction = model.predict(test_images)
predictions = probability_model.predict(test_images)

for i in range(len(predictions)):
    
    predictionItem = class_names[np.argmax(predictions[i])]
    real = class_names[test_labels[i]]

    plt.grid(False)
    plt.imshow(test_images[i], cmap=plt.cm.binary)
    plt.title("prediction : " + predictionItem + " | " + "Real : " + real)
    plt.show()