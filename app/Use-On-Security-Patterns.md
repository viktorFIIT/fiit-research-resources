# Use on Security Patterns

Let’s assume sequence of security patterns from the catalog of security patterns. Let’s denote names of the patterns in this sequence with first letters of the words in their names:

- ACR – stands for Access Control Requirements.
- SAP – stands for Single Access Point.
- SS – stands for Security Session.
- RBAC – stands for Role-Based Access Control.
- AUTH – stands for Authorization.
- ACL – stands for Access Control List.
- PBAC – Policy-Based Access Control.

**Kick-off sequence 1**:  ACR -> SAP -> SS -> RBAC -> AUTH -> ACL -> PBAC

User clicks on the “Insert Kick-off Sequence” button and inserts number of the patterns in catalog of security patterns and kick-off pattern sequence where patterns have abbreviated names. User constructs the stochastic tree for this kick-off pattern sequence by clicking on button “Construct Tree”.

![first-screen](https://github.com/viktorFIIT/fiit-research-resources/assets/32246112/e7bde952-a6fa-41aa-9162-9253b44f9135)

Once the stochastic tree is constructed user can search for the expected pattern sequence candidate on his own or he can speed up this process by clicking on button “Find candidate”.

![second-screen](https://github.com/viktorFIIT/fiit-research-resources/assets/32246112/3af93460-9119-4183-8ae0-dd1fd153296d)

After that, the expected pattern sequence candidate is displayed to the user along with probability of applying it. Users can start establishing expected pattern sequence from this sequence by clicking on button “Establish”.

![third-screen](https://github.com/viktorFIIT/fiit-research-resources/assets/32246112/b4701dff-0655-4066-86e3-078e9af66671)

User constructs pattern map of applicable patterns for first pattern in the expected pattern sequence candidate in the first tab. User is provided with pattern expected to be applied next after the first pattern in candidate sequence after clicking on “Find next applicable pattern” button. For patterns that are not present in the kick-off pattern sequence and there were identified in pattern map as applicable, probability of applying them before and after the first pattern in candidate sequence must be provided in modal window, such that symmetry of relationship can be computed.

![fourth-screen](https://github.com/viktorFIIT/fiit-research-resources/assets/32246112/fd3a7768-64ca-4c54-a797-24bb6b39221a)

After clicking on button labeled “Find next applicable pattern”, SAP was shown standing as abbreviation for Single Access Point security pattern that is expected to be applied after the ACR or Access Control Requirements. User can now continue to establish expected pattern sequence by clicking on button “Continue”.

![fifth-screen](https://github.com/viktorFIIT/fiit-research-resources/assets/32246112/b7e05347-7404-41c7-9476-f4a957c9027f)

After clicking on button labeled “Find next applicable pattern” another expected pattern can be provided. This time it was security pattern abbreviated as SS that stands for Security Session pattern.

![sixth-screen](https://github.com/viktorFIIT/fiit-research-resources/assets/32246112/0c2a78c6-acbf-46d9-af97-889ac68fc94c)

If the next applicable pattern is not in the kick-off pattern sequence, application asks for conditional probabilities:

![seventh-screen](https://github.com/viktorFIIT/fiit-research-resources/assets/32246112/5ab3cef9-b1e0-46fa-af53-58d330a730c0)

After providing all applicable patterns after the second expected pattern, use of the Role-Based Access Control security pattern was recommended by the app.

![eight-screen](https://github.com/viktorFIIT/fiit-research-resources/assets/32246112/01733193-8163-4e51-9fb9-079cc12c9468)

Users of the app can also look back any time they want by clicking on the tab name and see what decision led to recommending use of another expected pattern. In the screen below, users were interested in what pattern was recommended to be used after Single Access Point. It was Security Session, for which the last editable tab was created.

![second-tab](https://github.com/viktorFIIT/fiit-research-resources/assets/32246112/80bbbd2e-92a0-454c-a76f-0890a71d7e8c)