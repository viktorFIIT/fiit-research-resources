# How to use

Install Python and Jupyter Notebook or Jupyter Lab. Then run from this place as ```jupyter notebook```. Your app is then accessible at ```http://localhost:8889/?token=<your-token>```.

ANN is MLP and is implemented in Python 3.8.0, with TensorFlow: https://www.tensorflow.org/ and Keras: https://keras.io/. Keras is a handy facade of TensorFlow for those who don't want to work with a low-level API.

Dependencies:

- latest pip as Python's dependency manager, see https://pypi.org/project/pip/
- tensorflow for neural network API, see https://www.tensorflow.org/
- sklearn for data wrangling, see https://scikit-learn.org/stable/
- numpy for data wrangling, see https://numpy.org/
- pandas for data wrangling, see https://pandas.pydata.org/
- matplotlib for evaluation: see https://matplotlib.org/

How to establish pattern sequences with artificial neural networks:

1. prepare dataset
2. train neural network on this dataset implementing softmax regression model
3. identify first pattern in pattern sequence. This pattern is identified by the highest probability in all output vectors.
4. second-highest probability identifies second pattern in this pattern sequence...

Each run of this neural network provides you unique probabilities and leads to unique pattern sequences.
