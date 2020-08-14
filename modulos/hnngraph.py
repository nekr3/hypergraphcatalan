import matplotlib.pyplot as plt
import numpy as np
from matplotlib.pyplot import figure
figure(figsize=(12, 12))

k = []

mod = 5

f = open("hnnmod" + str(mod) + ".txt", "r+")

lines = f.readlines()

f.close()

for line in lines :
    parts = line.split("\n")[0].split("\t")
    j = []
    for p in parts :
        if (p == '') :
            j.append(0)
        else :
            j.append(int(p))
    k.append(j)

for l in k :
    while (len(l) < len(k[len(k)-1])) :
        l.append(0)

plt.imshow(np.array(k))
plt.savefig("hnnmod" + str(mod) + ".png", bbox_inches='tight')
