// creating object of http class
const http = new Http();

// creating function for calling delete() using http object 
async function deleteHotel(url) {
	console.log("Delete link clicked");
	const response = await http.delete(url);
	console.log(response);
}

