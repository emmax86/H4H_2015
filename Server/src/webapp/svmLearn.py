from sklearn import svm

class svmLearn():

    '''
    Initialization of the Support Vector Machine.
    '''
    def __init__(self, input_vectors, output_labels):
        self.machine = svm.SVC()
        self.machine.fit(input_vectors, output_labels)
    '''
    Use of trained Support Vector Machine to classify new
    data and insert it to new_output_label
    '''
    def classify(self, new_data):
        self.new_output_label = self.machine.predict(new_data)

    '''
    Return array of the newly labeled output values
    0 -> Defined as user NOT OK
    1 -> Defined as user IS OK
    '''
    def labeledNewData(self):
        return self.new_output_label
