
import sqlite3

db_path = r"c:\Users\User\AndroidStudioProjects\AdvanceEnglishDictionary\app\src\main\assets\db_prahse.db"
conn = sqlite3.connect(db_path)
cursor = conn.cursor()

print("Tables in db_prahse.db:")
cursor.execute("SELECT name FROM sqlite_master WHERE type='table';")
for table in cursor.fetchall():
    print(f"- {table[0]}")

print("\nCategories in commonUsefulPhrases:")
cursor.execute("SELECT id, categories FROM commonUsefulPhrases;")
for row in cursor.fetchall():
    print(f"{row[0]}: {row[1]}")

conn.close()
