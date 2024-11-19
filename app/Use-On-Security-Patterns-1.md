# Experiment with Security Patterns (EuroPLoP 2024)

Let’s assume the following sequence of security patterns from the catalog of security patterns. Let’s denote names of these patterns in this sequence with first letters of words in their names:

- ACR – stands for Access Control Requirements.
- SAP – stands for Single Access Point.
- SS – stands for Security Session.
- RBAC – stands for Role-Based Access Control.
- AUTH – stands for Authorization.
- ACL – stands for Access Control List.

**Kick-off sequence**:  ACR -> SAP -> SS -> RBAC -> AUTH -> ACL

User clicks on the “Insert Kick-off Sequence” button and inserts number of the patterns in the catalog of security patterns she works with and the kick-off pattern sequence. User constructs the stochastic tree for this kick-off pattern sequence by clicking on the button “Construct Tree”.

![kick-off](https://github.com/user-attachments/assets/cdfce3e3-222b-457b-8cc3-7285528a742a)

Once the stochastic tree is constructed, user can search for the expected pattern sequence candidate, or she can speed up this process by clicking on the button “Find candidate” instead. There can be multiple nodes in the stochastic tree with the highest probability:

![select-candidate](https://github.com/user-attachments/assets/0dd219fa-5d12-4f89-83e8-884464c01fba)

User constructs pattern map of applicable patterns for the first pattern in the expected pattern sequence candidate. User is provided with pattern expected to be applied next after the first pattern in candidate sequence after clicking on “Find next applicable pattern” button. For patterns that are not present in the kick-off pattern sequence and that were identified in pattern map as applicable, probability of applying them before and after the first pattern in candidate sequence must be provided in modal window, such that symmetry of relationship can be computed.

![insert-prob](https://github.com/user-attachments/assets/debc93b3-2b89-486e-a0a2-182b58eff159)

After clicking on the button labeled “Find next applicable pattern”, Single Access Point security pattern was identified as the one that is expected to be applied after the Access Control Requirements (or ACR). User can continue to establish expected pattern sequence by clicking on the button “Continue”.

![sap-after-acr](https://github.com/user-attachments/assets/ac83d63b-cf7e-4ae4-8b29-249fdfc7d1cd)

After clicking on the button labeled “Find next applicable pattern” another expected pattern can be provided. This time it was the security pattern abbreviated as SS that stands for the Security Session.

![previous-sap](https://github.com/user-attachments/assets/db9bb3ed-dca8-4009-983e-4db6c1c10115)
![next-is-ss](https://github.com/user-attachments/assets/27bfc4e9-6406-49f2-b531-bcfa8e8e29e9)

After providing all applicable patterns after the second expected pattern, use of the Role-Based Access Control security pattern was recommended by the app.

![rbac-after-ss](https://github.com/user-attachments/assets/c26b6e1c-97b6-459b-9a55-702bb1878677)