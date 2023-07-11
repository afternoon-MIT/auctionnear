package com.example.auctionnearapp

class Auction {
    private var auctionowner:String = ""
    private var auctionlocation:String = ""
    private var userid:String = ""
    private var startDate: String=""
    private var endDate: String=""


    constructor(
        auctionowner: String,
      auctionlocation: String,
        userid: String,
        startDate: String,
        endDate: String,

    ) {
        this.auctionowner = auctionowner
        this.auctionlocation= auctionlocation
        this.userid = userid
        this.startDate = startDate
        this.endDate = endDate


    }

    constructor() : this("", "", "", "", "")
}

