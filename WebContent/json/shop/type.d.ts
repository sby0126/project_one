interface Schema {
    genderType: String;
    shopType: String;
    offset: Offset;
    imageUrl: String;
    contentData: ContentData[];
    imageData: any;
}

interface Offset {
    start: number;
    count: number;
}

interface ContentData {
    category: String;
    shopName: String;
    texts: String;
    url: String;
}