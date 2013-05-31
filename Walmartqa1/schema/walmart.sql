create table item (
	itemid varchar(12) not null,
	itemdescr varchar(40) not null,
	itemprice double not null,
	localeId varchar(12) not null,
	PRIMARY KEY (itemid)
);

create table locale (
	localeId varchar(12) not null,
	localedescr varchar(40) not null,
	PRIMARY KEY (localeId)
);

create table stock (
	itemid varchar(12) not null,
	storeId varchar(12) not null,
	itemQty integer not null;
	CONSTRAINT item_store PRIMARY KEY(itemid,storeId)
);

create table store (
	storeId varchar(12) not null,
	storedescr varchar(40) not null,
	storeaddress varchar(40) not null;
	localeId varchar(12) not null,
	PRIMARY KEY (storeId)
);