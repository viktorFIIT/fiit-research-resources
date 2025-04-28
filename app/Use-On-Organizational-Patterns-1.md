# Experiment with Organizational Patterns

Let’s assume the following sequence of organizational patterns from the Piecemeal Growth pattern language. 
Let’s denote names of these patterns in this sequence with first letters of words in their names:

SST – denotes Self Selecting Team organizational pattern.
DG - denotes Diverse Groups organizational pattern.
UP - denotes Unity of Purpose organizational pattern.
PR - denotes Patron Role organizational pattern.
FW - denotes Fire Walls organizational pattern.
GK - denotes Gate Keeper organizational pattern.
CS - denotes Compensate Success organizational pattern.
STO - denotes Size the Organization organizational pattern.
PII – denotes Phasing It In organizational pattern.
APP - denotes Apprenticeship organizational pattern.
SV - denotes Solo Virtuoso organizational pattern.
DIP - denotes Developing in Pairs organizational pattern.
HD - denotes Holistic Diversity organizational pattern.
DER - denotes Domain Expertise in Roles organizational pattern.
SBS - denotes Subsystem by Skill organizational pattern.
MTN - denotes Moderate Truck Number organizational pattern.

**Kick-off sequence**:  SW -> SST -> DG -> UP -> PR -> FW -> GK -> CS -> STO -> PII -> APP -> SV -> DIP -> HD -> DER -> SBS -> MTN

This sequence is meaningful. 
Its use can be explained in the pattern story.
This sequence describes transformation of the small team experimenting with the new technology into the wider organizational structure after initial success of the software prototype this team was responsible for.
262 141 nodes are required to implement stochastic tree for this sequence, if each node must hold information about all its parents, along with probability of the use of each of these nodes representing pattern sequence.
Users of the application have the option to insert new nodes into the stochastic tree by inserting pattern name abbreviation into the relevant text field labeled “Pattern Abbrev.”. 
Value into the text field labeled “Number of patterns” must be inserted as well, and it’s the number of patterns user works with. 
Each node is added into the stochastic tree after clicking on the submit button “Insert node”. 
After that, the user does not have to specify the number of patterns anymore, but the text field for this number becomes non-editable.

![one-by-one](https://github.com/user-attachments/assets/d6367a23-805d-4945-9de4-e01efa5b6bc4)

To speed up inserting kick-off sequences and constructing stochastic trees on top of them, application provides option to upload them through the text area displayed after the click on the button labeled “Insert Kick-off Sequence”.

![sequence-in-text-area](https://github.com/user-attachments/assets/d2f7619b-398f-4566-bec0-779c7ba833d9)

Because this kick-off sequence consists of 17 patterns, a stochastic tree for this sequence would be too big to be displayed with JGraphT graphic library. 
This library only allows to draw a binary tree and functionality behind the stochastic tree must be implemented by the client side.
This graphic library is also very slow when it comes to loading a big graph, therefore, other graphic libraries should be explored as well.

Stochastic trees are used only to find an expected pattern sequence candidate. 
If the visualization of the stochastic tree would be too big to be visualized, the application stops visualizing it, provides warning and displays the expected pattern sequence candidate. 
The structure of the stochastic tree is constructed but its visualization is stopped.

![too-many-nodes](https://github.com/user-attachments/assets/037045d1-b251-4572-b095-7e44f6da6802)

As can be seen in the bottom picture, the application displays expected pattern sequence candidate along with the probability of applying it by the assumed domain expert.

![list-of-org-sequences](https://github.com/user-attachments/assets/b982a7d2-dd10-496f-b300-c34702be9887)

After clicking on the button “Establish Pattern Sequence”, user is redirected to the new tab where she can add additional applicable patterns after the first pattern in the candidate sequence into the pattern map of applicable patterns. 
Abbreviations for these patterns must be inserted into the text field labeled “Next applicable pattern abbreviation”. 
If some linked pattern links to the first pattern in candidate sequence in its text description, checkbox “Two-way relationship” must be checked.
If central pattern in the pattern map does not link to some pattern but this pattern links to this central pattern, checkbox "Link to central pattern" must be checked.

Each relationship between the central pattern in the pattern map and the additional applicable pattern is added into this map after clicking on the button "Insert node".

After each addition of the relationship into the pattern map, a new row is added into the table with the conditional probabilities and the symmetry of relationships. 
This table is displayed on the right side of the tab. This table consists of the four columns:

- A column “Relationship” that indicates the first pattern in the expected pattern sequence candidate links to the additional pattern.
- A column “Probability” that displays conditional probability of applying additional applicable pattern after the central pattern in the pattern map. This probability is extracted from the stochastic tree.
- A column “Opposite Relationship” that provides inverse probability to the conditional probability and this opposite probability is calculated with the Bayes rule. It is a conditional probability of applying an additional applicable pattern before application of the central pattern in the pattern map.
- A column “Symmetry of Relationship” provides symmetry of the relationship between the central pattern in the pattern map and each additional applicable pattern. This value is calculated as the absolute value of the difference between values displayed in columns “Probability” and “Opposite probability”.

Pattern expected to be applied after the central pattern in the pattern map is the one that has the strongest symmetry (the lowest number in the column “Symmetry of Relationship”) of the relationship with the central pattern in this map. 
Name of this next expected pattern is provided to the user after clicking on the button “Find next applicable pattern”.

![sst-after-sw](https://github.com/user-attachments/assets/db0d959d-2ac3-429f-a66f-afcbf49a859a)

After clicking on button “Continue”, user is redirected to another tab where she continues doing the same. 
User is asked to provide other applicable patterns after the pattern that was previously identified as pattern expected to be applied next. 
Strengths of symmetries of relationships are calculated again and provided on the table on the right-hand side. 
This time, the next applicable pattern was DG = Diverse Groups. 
This makes an expected pattern sequence SW -> SST -> DG standing for Skunk Works -> Self-Selecting Team -> Diverse Groups.

![dg-after-sst](https://github.com/user-attachments/assets/407f3cc3-e473-46d4-af10-29c6b206b6b3)