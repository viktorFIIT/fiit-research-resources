# Establishing Sequences of Organizational Patterns With Artificial Neural Networks

This repository helps you to establish sequences of organizational patterns from all 4 languages of organizational patterns documented by Coplien and Harrison in Organizational Patterns of Agile Software Development.

Pattern stories to all established pattern sequences can be found at [pattern stories site](https://github.com/viktorFIIT/fiit-research-resources/tree/main/neural-network/pattern-stories)

Following pattern sequences were established:

**From first experiment with bigrams**:

1. Subclass Per Team (p=0.799468) -> Patron Role (p=0.674086) -> Hierarchy of Factories (p=0.147888) from bigrams. <br>
This pattern sequences introduces experienced person to decide when is the right place to optimize hierarchies of classes into the Hierarchy of Factories design pattern. This pattern sequence is established from patterns in People and Code Pattern Language and Piecemeal Growth Pattern Language. This pattern sequence was established using explicit relationship (Hierarchy of Factories implements Subclass Per Team) and implicit relationship between its patterns.

![first-pattern-sequence drawio](https://user-images.githubusercontent.com/32246112/236413228-8e4e0895-49ce-4314-a8f1-53f0dbd5feed.png)


2. Private Versioning (p=0.942623) -> Phasing it in (p=0.731583) -> Generics and Specifics (p=0.436626) from bigrams. <br>
This pattern sequence can be used to implement solution expected to be used across many software projects. Private Versioning pattern starts this sequence because of the need to experiment with new technology. Oncestan the owner of the application where new technology is to be used is ready, another experienced software architect comes into the team by developing generic framework with UI components. Code behind these UI components is written by the less experienced developer and UI components are used as building blocks of the application-specific solution. This application-specific solution is a proof of concept for other software projects waiting to be integrated with the solution based on this new technology in the future. This pattern sequence is established from patterns which have only implicit relationships between them.

![second-pattern-sequence drawio](https://user-images.githubusercontent.com/32246112/236458144-dbe09816-ae59-4ea5-9a59-9079da470fc9.png)


**From first experiment with trigrams**:

1. Apprenticeship (p=0.998081) -> Domain Expertise In Roles (p=0.063485) -> Standup Meeting (p=0.061498) from trigrams. <br>
This pattern sequence is about novice developers working together with senior developers who are experts in their field, according to Apprenticeship1. The development team consists of the subteams responsible for particular products as per the Domain Expertise In Roles2. These subteams meet according to Standup Meeting3 to discuss what’s going on and work plans for the future. <br>
Pattern story for this pattern sequence can be found [here](https://github.com/viktorFIIT/fiit-research-resources/blob/main/neural-network/pattern-stories/first-pattern-story-from-trigrams.png).
![sequence drawio (3)](https://github.com/viktorFIIT/fiit-research-resources/assets/32246112/357ac30f-c101-4d7e-abb2-17987a9d8818)

2. Architecture Team (p=0.057077) -> Few Roles (p=0.055865) -> Generics and Specifics (p=0.055788) from trigrams. <br>
Experienced senior developers choose the technology to work with, and then according to Architecture Team1 design the initial software product architecture. Some less experienced software developers are then according to Few Roles2 and Generics and Specifics3 chosen to incorporate and customize this framework in the specific software products. <br>
Pattern story for this pattern sequence can be found [here](https://github.com/viktorFIIT/fiit-research-resources/blob/main/neural-network/pattern-stories/second-pattern-story-from-trigrams.png)
![sequence drawio (4)](https://github.com/viktorFIIT/fiit-research-resources/assets/32246112/c0103735-4ef1-4cd5-bd2d-31c2794821c2)


**From second experiment with bigrams**:

1. Architect Controls Product (p=0.228964) -> Few Roles (p=0.126118) -> Code Ownership (p=0.119162) from bigrams <br>
This pattern sequence documents usual setting in software house companies, where skilled and experienced developer is assigned application owner role. He still can have consultants assigned or it's own developer to help, but everything flows through the owner hands. This pattern sequence is partially based on implicit relationships between its patterns because Architect Controls Product makes reference to Code Ownership (as architect can code too).

![fifth-pattern-sequence drawio](https://user-images.githubusercontent.com/32246112/236620677-61e70dc4-7be2-48bd-a59d-d7d50dd05568.png)

2. Generics and Specifics (p=0.562330) -> Distribute Work Evenly (p=0.356492) -> Architect Controls Product (p=0.238690). <br>
This pattern documents situation where framework and possibly its application in another code base or product is developed and maintained by various roles. Build of the final product is supervised by architect who also directs the architectural style of this framework. This pattern sequence is based completely on implicit relationships between its patterns.

![sixth-pattern-sequence drawio](https://user-images.githubusercontent.com/32246112/236622100-6eef5b7c-33d3-4d03-9b33-2f00e25358c4.png)

**From second experiment with trigrams**:

1. Organization Follows Market (p=0.060326) -> Developing In Pairs (p=0.060123) -> Architecture Team (p=0.059011) from trigrams. <br>
This pattern sequence is about having the core team whose members experiment with new technology and try to design initial architecture of the already existing system based on this new technology as part of the migration step.


2. Smoke Filled Room (p=0.063462) -> Code Ownership (p=0.057482) -> Lock Em Up Together (p=0.054356) from trigrams. <br>
This pattern sequence can be used to devise solution which is going to impact multiple software projects or products at once. This solution is expected to be well designed by the tycoons in the development staff.
