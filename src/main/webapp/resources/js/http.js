class Http {
	// delete() 
	async delete(url) {
		const response = await fetch(url, {
			method : 'DELETE'
		});
		return "Deleted";
	}
}