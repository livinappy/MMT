# MMT 0.11 - ALPHA RELEASE 

## About MMT
MMT is a context-aware, incremental and distributed general purpose Machine Translation technology.

MMT goal is to make MT easy to adopt and scale.

With MMT you don't need anymore to train multiple custom engines for each of your domains/projects/customers, you can push all your data to a single engine that will automatically and in real-time adapt to the context you provide.

MMT aims to deliver the quality of a custom engine and the low sparsity of your all data combined.

You can find more information on: http://www.modermmt.eu


## About this Release

This application is the binary version of MMT (open source distribution expected by the end 2016). 

This MMT release will allow you to create an MT engine, available via a REST API, given your training data (folder with line aligned text files)
Ex. domain1.en domain1.it domain2.en domain2.it 
In general:
<domain-id>.<2 letters iso lang code|5 letters RFC3066>

Note: domain-id must be [a-zA-Z0-9] only without spaces.

## Your first translation with MMT

Assuming you read INSTALL.

### Create an Engine

We included a very small dataset, just to verify that training works.

```bash
./mmt create engine en it examples/data/train
```

### To Start/Stop an existing engine
```bash
./mmt start|stop
```

### Translate via API

http://localhost:8000/translate?q=party&context=President

### MMT Tuning (Expert)

MMT quality can be increased by tuning the parameters providing unseen translation examples. 

```
./mmt tune examples/data/dev
```

### MMT distributed (Expert)

Let's distribute MMT to a second machine. Login into the new second machine and run

```bash 
./mmt start --master user:pass@3.14.15.16
or for private key auth 
./mmt start --master user@3.14.15.16 --master-pem master-credentials.pem
```

3.14.15.16 being the IP address of the machine where is.
master-credentials.pem being your ssh key to the master machine for rsync.

The engine will be synced from the master and translation requests will be load balanced across the 2 istances.
Only the master will respond to the Translation API and distribute load.

Every time you start a slave the model data will be rsynced to the slave.

Note: rsyncing  of the models has been temporanealy disabled in 0.11 and models files have to be copied manually. To test the release please contact davide.caroselli@translated.net 

## How to prepare your data

MMT uses standard sentence aligned corpora, optionally divided into files by domain. 

Example:
```
data/microsoft.en
data/microsoft.fr
data/europarl.en
data/europarl.fr
data/wmt10.en
data/wmt10.fr
```

In general:
```
domain-id.(2 letters iso lang code|5 letters RFC3066)
```

Note: domain-id must be [a-zA-Z0-9] only without spaces.
