# Experiment with Organizational Patterns (Springer Nature)

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