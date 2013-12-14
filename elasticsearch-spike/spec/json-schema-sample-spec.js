describe("JaySchema", function () {
    it("validates json document according to HL7 FHIR schema", function (done) {
        var JaySchema = require('jayschema');
        var jaySchema = new JaySchema();

        var fhirSchema = {
            definitions: {
                Identifier: {
                    id: 'Identifier',
                    type: 'object',
                    properties: {
                        use: {type: 'string'},
                        system: {type: 'string'},
                        value: {type: 'string'}
                    }
                },
                HumanName: {
                    id: 'HumanName',
                    type: 'object',
                    properties: {
                        use: {type: 'string'},
                        family: {type: 'string'},
                        given: {type: 'string'}
                    }
                },
                Patient: {
                    id: 'Patient',
                    type: 'object',
                    properties: {
                        identifier: {
                            type: 'array',
                            items: {
                                '$ref': 'Identifier'
                            }
                        },
                        name: {
                            type: 'array',
                            items: {
                                '$ref': 'HumanName'
                            }
                        }
                    }
                }
            },
            '$ref': 'Patient'
        };

        jaySchema.validate("invalid-object", fhirSchema, function (errors) {
            expect(errors).not.toBe(undefined);
            done();
        });

        var validPatientInstance = {
            identifier: [
                {
                    use: 'global',
                    system: 'mpi',
                    value: 'global-id-12345'
                },
                {
                    use: 'usual',
                    system: 'emr',
                    value: 'emr-id-67890'
                }
            ],
            name: [
                {
                    use: 'usual',
                    family: 'Xiong',
                    given: 'Jie'
                }
            ]
        };

        jaySchema.validate(validPatientInstance, fhirSchema, function (errors) {
            expect(errors).toBe(undefined);
            done();
        });
    });
});