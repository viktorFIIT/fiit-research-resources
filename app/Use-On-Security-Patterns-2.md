# Experiment with Security Patterns (IEEE Access)

Let's test the hypothesis if the Asymmetric Encryption -> Third Party-Based Authentication -> Mutual Authentication is the most expected pattern sequence.

Let’s assume the following sequence of security patterns from the catalog of security patterns. Let’s denote names of these patterns in this sequence with first letters of words in their names:

- AE – stands for Asymmetric Encryption.
- TPBA – stands for Third Party-Based Authentication.
- MA – stands for Mutual Authentication.
- C – stands for Credential.
- SS – stands for Security Session.
- CP – stands for Check Point.

**Kick-off sequence**:  AE -> TPBA -> MA -> C -> SS -> CP

User clicks on the “Insert Kick-off Sequence” button and inserts number of the patterns in the catalog of security patterns she works with and the kick-off pattern sequence. User constructs the stochastic tree for this kick-off pattern sequence by clicking on the button “Construct Tree”.

![new-kick-off](https://github.com/user-attachments/assets/2ceb7499-9fb2-4214-867c-30b06f00fa46)

Once the stochastic tree is constructed, user can search for the expected pattern sequence candidate, or she can speed up this process by clicking on the button “Find candidate” instead. There can be multiple nodes in the stochastic tree with the highest probability:

![selecting-candidate](https://github.com/user-attachments/assets/078250d9-5448-42c5-8d3b-cddc20fa2b24)

User constructs pattern map of applicable patterns for the first pattern in the expected pattern sequence candidate. User is provided with pattern expected to be applied next after the first pattern in candidate sequence after clicking on “Find next applicable pattern” button. For patterns that are not present in the kick-off pattern sequence and that were identified in pattern map as applicable, probability of applying them before and after the first pattern in candidate sequence must be provided in modal window, such that symmetry of relationship can be computed.

Asymmetric Encryption mentions or is mentioned by following patterns that can be applied next:

- Symmetric Encryption (bidirectional relationship with Asymmetric Encryption)
- Secure Channels (bidirectional relationship with Asymmetric Encryption)
- Third Party-Based Authentication - because Third Party-Based Authentication mentions Asymmetric Encryption as it can be implemented with it. There's no link from Asymmetric Encryption to the Third Party-Based Authentication. Because of that checkbox "Link to central pattern" must be checked while adding this relationship.

Use of the Third Party-Based Authentication pattern after Asymmetric Encryption was recommended by the app because strongest symmetry of relationship was found between Asymmetric Encryption and Third Party-Based Authentication patterns.

![tpba-after-ae](https://github.com/user-attachments/assets/61360fa5-0ae8-461b-b207-0570a3d5d82e)

Use of the Asymmetric Encryption pattern after Third Party-Based Authentication was recommended by the app because strongest symmetry of relationship was found between Third Party-Based Authentication and Asymmetric Encryption patterns.

![Screenshot 2024-11-19 124512](https://github.com/user-attachments/assets/f18e0d2e-bd70-486a-939c-44be0d024848)

Pattern sequence Asymmetric Encryption -> Third Party-Based Authentication -> Mutual Authentication is not the expected pattern sequence based on the strongest symmetry of relationships between patterns.

