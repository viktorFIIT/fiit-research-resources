# Establishing Sequences of Organizational Patterns With Artificial Neural Networks

This repository helps you to establish sequences of organizational patterns from all 4 languages of organizational patterns documented by Coplien and Harrison in Organizational Patterns of Agile Software Development.

Pattern stories to all established pattern sequences can be found at [pattern stories site](https://github.com/viktorFIIT/fiit-research-resources/tree/main/neural-network/pattern-stories)

How to establish pattern sequences with artificial neural networks:

1. prepare dataset
2. train neural network on this dataset implementing softmax regression model
3. identify first pattern in pattern sequence. This pattern is identified by the highest probability in all output vectors.
4. second-highest probability identifies second pattern in this pattern sequence...

Each run of this neural network provides you unique probabilities and leads to unique pattern sequences.

Following pattern sequences were identified:

From experiment 1:

1. Subclass Per Team (p=0.799468) -> Patron Role (p=0.674086) -> Hierarchy of Factories (p=0.147888) from bigrams. <br>
This pattern sequences introduces experienced person to decide when is the right place to optimize hierarchies of classes into the Hierarchy of Factories design pattern. This pattern sequence is established from patterns in People and Code Pattern Language and Piecemeal Growth Pattern Language. This pattern sequence was established using explicit relationship (Hierarchy of Factories implements Subclass Per Team) and implicit relationship between its patterns.

![first-pattern-sequence drawio](https://user-images.githubusercontent.com/32246112/236413228-8e4e0895-49ce-4314-a8f1-53f0dbd5feed.png)


2. Private Versioning (p=0.942623) -> Phasing it in (p=0.731583) -> Generics and Specifics (p=0.436626) from bigrams. <br>
This pattern sequence can be used to implement solution expected to be used across many software projects. Private Versioning pattern starts this sequence because of the need to experiment with new technology. Oncestan the owner of the application where new technology is to be used is ready, another experienced software architect comes into the team by developing generic framework with UI components. Code behind these UI components is written by the less experienced developer and UI components are used as building blocks of the application-specific solution. This application-specific solution is a proof of concept for other software projects waiting to be integrated with the solution based on this new technology in the future. This pattern sequence is established from patterns which have only implicit relationships between them.

![second-pattern-sequence drawio](https://user-images.githubusercontent.com/32246112/236458144-dbe09816-ae59-4ea5-9a59-9079da470fc9.png)

3. Apprenticeship (p=0.998081) -> Domain Expertise In Roles (p=0.063485) -> Standup Meeting (p=0.061498) from trigrams. <br>
This pattern sequence is about novice developers working together with senior developers who are experts in their field, according to Apprenticeship1. The development team consists of the subteams responsible for particular products as per the Domain Expertise In Roles2. These subteams meet according to Standup Meeting3 to discuss what’s going on and work plans for the future.

![sequence drawio (3)](https://github.com/viktorFIIT/fiit-research-resources/assets/32246112/357ac30f-c101-4d7e-abb2-17987a9d8818)

4. Architecture Team (p=0.057077) -> Few Roles (p=0.055865) -> Generics and Specifics (p=0.055788) from trigrams. <br>
Experienced senior developers choose the technology to work with, and then according to Architecture Team1 design the initial software product architecture. Some less experienced software developers are then according to Few Roles2 and Generics and Specifics3 chosen to incorporate and customize this framework in the specific software products. 

![sequence drawio (4)](https://github.com/viktorFIIT/fiit-research-resources/assets/32246112/c0103735-4ef1-4cd5-bd2d-31c2794821c2)

From experiment 2:

1. Architect Controls Product (p=0.228964) -> Few Roles (p=0.126118) -> Code Ownership (p=0.119162)
This pattern sequence documents usual setting in software house companies, where skilled and experienced developer is assigned application owner role. He still can have consultants assigned or it's own developer to help, but everything flows through the owner hands. This pattern sequence is partially based on implicit relationships between its patterns because Architect Controls Product makes reference to Code Ownership (as architect can code too).

![fifth-pattern-sequence drawio](https://user-images.githubusercontent.com/32246112/236620677-61e70dc4-7be2-48bd-a59d-d7d50dd05568.png)


2. Generics and Specifics (p=0.562330) -> Distribute Work Evenly (p=0.356492) -> Architect Controls Product (p=0.238690). This pattern documents situation where framework and possibly its application in another code base or product is developed and maintained by various roles. Build of the final product is supervised by architect who also directs the architectural style of this framework. This pattern sequence is based completely on implicit relationships between its patterns.

![sixth-pattern-sequence drawio](https://user-images.githubusercontent.com/32246112/236622100-6eef5b7c-33d3-4d03-9b33-2f00e25358c4.png)


4. Architect Also Implements (p=0.995495) -> Code Ownership (p=0.061411) -> Lock Em Up Together (p=0.059739)
This pattern sequence documents how initial system architecture is designed  by the team led by lead developer who is an architect and understand code and who directs the architectural style of the product by leading team of other programmers and designers. This pattern sequence is only partially based on implicit relationships between patterns because Code Ownership mentions Architect Also Implements organizational pattern, because architect is expected to understand code too.  Lock Em Up Together also mentions Architect Also Implements, because architects understanding the code are locked in the room to desing initial architecture.

![seventh-pattern-sequence drawio](https://user-images.githubusercontent.com/32246112/236625448-8842fb62-7e22-4973-9884-d08d5ece00dd.png)


4. Developing In Pairs (p=0.055370) -> Distribute Work Evenly (p=0.054725) -> Architecture Team (p=0.051247)
This pattern sequence documents how the system is developed. Developers write the code (Developing In Pairs). Because they want to conduct code review as soon as possible and because some developers are more skilled and experienced than the others, they write the code in pairs. Developers are organized into small groups (or even one group, one pair) headed by architects of the partitions. System is partitioned and it's architecture is designed by high level architects because of the application of Architecture Team organizational pattern.
This pattern sequence is based solely on the implicit relationships between its patterns.

![eight-pattern-sequence drawio](https://user-images.githubusercontent.com/32246112/236638733-382721d2-4ee0-472c-8bda-da1bda0ad2bf.png)


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

# Why we should use artificial neural networks to search for implict relationships between organizational patterns?

Simply because they are one of the most efficient techniques how we can encode information about the real world
in its artificial representation. We encode this information in hidden layers of the artificial neural network. 
This is one of the accepted techniques which allows us to learn artificial representations of patterns. Learned representations
are widely considered to be much more efficient than a hand-crafted representations (e.g. with Prolog or through 
other languages for building knowledge base.)

# Why do we need to use multiple-layer artificial neural networks to search for implicit relationships between organizational patterns?

Each of the layers represent information about the pattern in some level. Combination of layers together enables us
solve our problem. Thus we try to solve our problem by decomposing bigger problem to its smaller parts - layers of the ANN.
Almost all ML techniques can be transformed to their neural network representation (ANN with 3 layers max.)

# Why we should use deep learning techniques instead of the machine learning techniques?

Deep learning techniques allow us to solve tasks which cannot be solved with simple machine learning techniques. 
They allow us to represent information about the processes described in organizational patterns and learn these
representations automatically. This means to automatically enhance them to have better representations.

Convolutional Neural Networks are a great way how we can search for patterns as flexibly as we can. Convolutional
Neural Networks can be used to connect multiple patterns to create bigger whole, because of their ability to search
for global tendencies. Multiple-layer perceptrons, in the other hand, can be only used to search for local tendencies.

See more about the CNN if you're interested here https://d2l.ai/chapter_convolutional-neural-networks/index.html

# Why do we talk about the multi-layer perceptrons instead of the neurons or perceptrons?

Logical neuron originally designed by Pitts & McCulloch in 1943 works only with integer or with binary-encoded attributes.
Their logical neuron also assumes that all attributes in a dataset have a same weight. In our case, this would mean
that all n-grams for some organizational pattern in a matrix of Waseeb et al. in 'Extracting Relations Between
Organizational Patterns Using Association Mining' on page 4. Table 1 would contribute same to the final output
of our probability model. Activation function for this logical neuron creates another problem for us. It does
not exhibit information ahead through the network if sum of the n-gram incidents is not bigger than some predefined threshold T.

# What is the biggest advantage of ANN and deep learning techniques? Why we should use these techniques here?

Artificial neural networks allow us to generalize and abstract information we find in the study of organizational
patterns. Deep learning techniques allow us to learn these representations and enhance them. 
