describe("ElasticSearch", function () {
    var indexName = 'shivaganda-mpi';
    var typeName = 'patient';

    var sage = require('sage');
    var es = sage("http://localhost:9200");
    var esi = es.index(indexName);
    var est = esi.type(typeName);

    beforeEach(function (done) {
        esi.destroy(function (err, result) {
            done();
        });
    });

    describe("create", function () {
        it("increases the amount of record by 1", function (done) {
            var patient = {
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

            est.post(patient, function () {
                esi.refresh(function () {
                    var search = {query: {field: {'identifier.use': 'global'} }};
                    es.index(indexName).type(typeName).find(search, function (err, result) {
                        expect(result.length).toBe(1);
                        done();
                    });
                });
            });
        });
    });
});