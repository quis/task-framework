![](https://digital.nhs.uk/media/89/NHSDigital/variant1/NHS-Digital-logo_WEB_LEFT-100x855)

# e-RS Task Framework 
> Base utility module for building a task scheduling function

## Background & Goals

The goal of this software is to provide a generic base from which specific task frameworks may be derived.
Software concepts relevent to the software include;

* Tasks
* States
* Queues

The common framework orchestrates each Task's lifecycle through mulitple "states" using a series of state-based queues.  (These queues are currently persisted in an a database)

## Usage example

Implementations of this common framework should define the type of tasks they perform, the type of backing store/other persistent mechanism used to configure each Task and details of derived state.  

The common framework is agnostic to these details, but provides a hook by which each task's configuration may be persisted within it in the common database store.

## Meta

### Contact
NHS Digital Technical Architecture – ers.ta@nhs.net

If you are looking to build this framework into your software and would like further information please contact us at ers.ta@nhs.net.

### License
Distributed under the Apache License, Version 2.0. See ``LICENSE.txt`` for more information.

### About the e-Referrals programme
The NHS e-Referral Service combines electronic booking with a choice of place, date and time for first hospital or clinic appointments. Patients can choose their first hospital or clinic appointment, book it in the GP surgery, online or on the phone.

### About NHS Digital
NHS Digital exists to improve health and care by providing national information, data and IT services for patients, clinicians, commissioners and researchers.

### Reference

* NHS Digital: https://digital.nhs.uk/referrals
* NHS e-Referals Programme: https://digital.nhs.uk/referrals
* NHS England Open Source Twitter: https://twitter.com/nhsopensource?lang=en