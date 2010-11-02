
CREATE TABLE MessageQueue (
    id                      INTEGER     AUTO_INCREMENT      NOT NULL,
    messageQueueName        VARCHAR(50)                     NOT NULL,
    named                   INTEGER     DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX mq_mqn(messageQueueName)
) TYPE=InnoDB;

CREATE TABLE MessageQueueService (
    id                      INTEGER     AUTO_INCREMENT      NOT NULL,
    messageQueueId          INTEGER                         NOT NULL,
    service                 VARCHAR(50)                     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (messageQueueId) REFERENCES MessageQueue (id)
) TYPE=InnoDB;

CREATE TABLE Message (
    id                      VARCHAR(50)                     NOT NULL,
    messageQueueId          INTEGER,
    messageCreator          VARCHAR(50)                     NOT NULL,
    sessionId               VARCHAR(50)                     NOT NULL,
    messageType             INTEGER                         NOT NULL,
    messageRoutingType      INTEGER                         NOT NULL,
    priority                INTEGER                         NOT NULL,
    target                  VARCHAR(254),
    reply                   INTEGER                         NOT NULL,
    fromURL                 VARCHAR(254)                    NOT NULL,
    replyURL                VARCHAR(254),
    targetNamedQueue        VARCHAR(50),
    replyNamedQueue         VARCHAR(50),
    correlationId           VARCHAR(254),
    acknowledged            INTEGER                         NOT NULL,
    messageState            INTEGER                         NOT NULL,
    created                 TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    retries                 INTEGER                         NOT NULL,
    processed               TIMESTAMP                       NOT NULL,
    nextProcess             TIMESTAMP                       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (messageQueueId) REFERENCES MessageQueue (id)
) TYPE=InnoDB;


CREATE TABLE MessageService (
    id                      INTEGER     AUTO_INCREMENT      NOT NULL,
    messageId               VARCHAR(50)                     NOT NULL,
    service                 VARCHAR(100)                    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (messageId) REFERENCES Message (id) ON DELETE CASCADE
) TYPE=InnoDB;


CREATE TABLE MessageProperty (
    id                      INTEGER     AUTO_INCREMENT      NOT NULL,
    messageId               VARCHAR(50)                     NOT NULL,
    name                    VARCHAR(50)                     NOT NULL,
    boolValue               INTEGER,
    byteValue               INTEGER,
    doubleValue             DOUBLE,
    floatValue              FLOAT,
    intValue                INTEGER,
    longValue               BIGINT,
    stringValue             VARCHAR(1024),
    objectValue             BLOB(1024),
    PRIMARY KEY (id),
    FOREIGN KEY (messageId) REFERENCES Message (id) ON DELETE CASCADE
) TYPE=InnoDB;


CREATE TABLE MessageTxtBody (
    messageId               VARCHAR(50)                     NOT NULL,
    body                    VARCHAR(1024)                   NOT NULL,
    PRIMARY KEY (messageId),
    FOREIGN KEY (messageId) REFERENCES Message (id) ON DELETE CASCADE
)  TYPE=InnoDB;


CREATE TABLE MessageRpcBody (
    messageId               VARCHAR(50)                     NOT NULL,
    xml                     VARCHAR(1024)                   NOT NULL,
    generatedException      INTEGER     DEFAULT 0000,
    resultValue             BLOB(1024),
    exceptionValue          BLOB(1024),
    PRIMARY KEY (messageId),
    FOREIGN KEY (messageId) REFERENCES Message (id) ON DELETE CASCADE
) TYPE=InnoDB;


CREATE TABLE MessagePrincipal (
    id                      INTEGER     AUTO_INCREMENT      NOT NULL,
    messageId               VARCHAR(50)                     NOT NULL,
    principalValue          VARCHAR(50)                     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (messageId) REFERENCES Message (id) ON DELETE CASCADE
) TYPE=InnoDB;


CREATE TABLE MessageError (
    id                      INTEGER     AUTO_INCREMENT      NOT NULL,
    messageId               VARCHAR(50)                     NOT NULL,
    errorLevel              INTEGER                         NOT NULL,
    errorDate               TIMESTAMP                       NOT NULL,
    msg                     VARCHAR(1024)                   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (messageId) REFERENCES Message (id) ON DELETE CASCADE
) TYPE=InnoDB;

