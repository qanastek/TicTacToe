import numpy as np
import argparse
import imutils
import random
import cv2
import sys
import os

ROOT_DIR = "cats_and_dogs_filtered/train"

noise_types = ["gauss","speckle"]

def noisy(noise_type, image):

	if noise_type == "gauss":

		row,col,ch= image.shape
		mean = 0
		var = 0.1
		sigma = var**0.5
		gauss = np.random.normal(mean,sigma,(row,col,ch))
		gauss = gauss.reshape(row,col,ch)
		noisy = image + gauss

		return noisy

	elif noise_type == "speckle":

		row,col,ch = image.shape
		gauss = np.random.randn(row,col,ch)
		gauss = gauss.reshape(row,col,ch)        
		noisy = image + image * gauss

		# cv2.imshow("speckle (Correct)", noisy)
		# cv2.waitKey(0)

		return noisy

def rotateImage(angle, image):

	rotated = imutils.rotate_bound(image, angle)

	return rotated

def dirtifyRotateImages(PATH_CATEGORY, image_name, image):

	# Generate 4 angles to the image
	for angle in range(90,360,90):

		# Rotate
		image = rotateImage(angle, image)

		# For each noise type
		for noise_type in noise_types:

			# Make it noisy
			image = noisy(noise_type,image)

			# Save It
			image_path = PATH_CATEGORY + "/" + str(angle) + "_" + noise_type + "_" + image_name

			cv2.imwrite(image_path, image)

def dirtifyImagesMain(ROOT_DIR):
	
	for category in os.listdir(ROOT_DIR):

		PATH_CATEGORY = os.path.join(ROOT_DIR,category)

		for image_name in os.listdir(PATH_CATEGORY):

			FULL_PATH = PATH_CATEGORY + "/" + image_name

			# print("IMAGE: " + image_name)

			image = cv2.imread(FULL_PATH, cv2.IMREAD_UNCHANGED)

			dirtifyRotateImages(PATH_CATEGORY, image_name, image)

dirtifyImagesMain(ROOT_DIR)